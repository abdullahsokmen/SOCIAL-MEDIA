package com.abdullah.service;

import com.abdullah.dto.request.NewCreateUserRequestDto;
import com.abdullah.exception.ErrorType;
import com.abdullah.exception.UserManagerException;
import com.abdullah.mapper.IUserMapper;
import com.abdullah.repository.IUserProfileRepository;
import com.abdullah.repository.entity.UserProfile;
import com.abdullah.repository.enums.EStatus;
import com.abdullah.utility.ServiceManager;
import org.springframework.stereotype.Service;

import javax.xml.parsers.SAXParser;
import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,Long> {

    private final IUserProfileRepository repository;


    public UserProfileService(IUserProfileRepository repository) {
        super(repository);
        this.repository = repository;
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
}
