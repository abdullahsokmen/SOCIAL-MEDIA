package com.abdullah.service;

import com.abdullah.dto.request.NewCreateUserRequestDto;
import com.abdullah.dto.request.UserProfileUpdateRequestDto;
import com.abdullah.exception.ErrorType;
import com.abdullah.exception.UserManagerException;
import com.abdullah.mapper.IUserMapper;
import com.abdullah.repository.IUserProfileRepository;
import com.abdullah.repository.entity.UserProfile;
import com.abdullah.repository.enums.EStatus;
import com.abdullah.utility.JwtTokenManager;
import com.abdullah.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,Long> {

    private final IUserProfileRepository repository;
    private final JwtTokenManager jwtTokenManager;


    public UserProfileService(IUserProfileRepository repository, JwtTokenManager jwtTokenManager) {
        super(repository);
        this.repository = repository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public Boolean createUser(NewCreateUserRequestDto dto) {
        try {
            save(IUserMapper.INSTANCE.toUserProfile(dto));
            return true;
        }catch (Exception e){
           throw new UserManagerException(ErrorType.USER_NOT_CREATED);
        }

    }

    public Boolean activateStatus(Long authId) {
        Optional<UserProfile> userProfile=repository.findOptionalByAuthId(authId);
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setEStatus(EStatus.ACTIVE);
        update(userProfile.get());
        return true;
    }

    public Boolean update(UserProfileUpdateRequestDto dto){
        Optional<Long>authId=jwtTokenManager.getIdFromToken(dto.getToken());
        if (authId.isEmpty()){
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> userProfile=repository.findOptionalByAuthId(authId.get());
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setPhone(dto.getPhone());
        userProfile.get().setAvatar(dto.getAvatar());
        userProfile.get().setAdress(dto.getAdress());
        userProfile.get().setEmail(dto.getEmail());
        userProfile.get().setAbout(dto.getAbout());
        update(userProfile.get());
        return true;

    }

}
