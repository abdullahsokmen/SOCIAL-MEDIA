package com.abdullah.service;

import com.abdullah.dto.request.ActivateStatusDto;
import com.abdullah.dto.request.NewCreateUserRequestDto;
import com.abdullah.dto.request.UpdateEmailOrUsernameRequestDto;
import com.abdullah.dto.request.UserProfileUpdateRequestDto;
import com.abdullah.exception.ErrorType;
import com.abdullah.exception.UserManagerException;
import com.abdullah.manager.IAuthManager;
import com.abdullah.mapper.IUserMapper;
import com.abdullah.rabbitmq.model.RegisterModel;
import com.abdullah.rabbitmq.producer.RegisterProducer;
import com.abdullah.repository.IUserProfileRepository;
import com.abdullah.repository.entity.UserProfile;
import com.abdullah.repository.enums.EStatus;
import com.abdullah.utility.JwtTokenManager;
import com.abdullah.utility.ServiceManager;
import org.apache.el.parser.Token;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {

    private final IUserProfileRepository repository;
    private final JwtTokenManager jwtTokenManager;
    private final IAuthManager authManager;
    private final CacheManager cacheManager;
    private final RegisterProducer registerProducer;


    public UserProfileService(IUserProfileRepository repository, JwtTokenManager jwtTokenManager, IAuthManager authManager, CacheManager cacheManager, RegisterProducer registerProducer) {
        super(repository);
        this.repository = repository;
        this.jwtTokenManager = jwtTokenManager;
        this.authManager = authManager;
        this.cacheManager = cacheManager;
        this.registerProducer = registerProducer;
    }

    public Boolean createUser(NewCreateUserRequestDto dto) {
        try {
            save(IUserMapper.INSTANCE.toUserProfile(dto));
            return true;
        }catch (Exception e){
           throw new UserManagerException(ErrorType.USER_NOT_CREATED);
        }

    }
    public Boolean createUserWithRabbitMq(RegisterModel model) {
        try {
           UserProfile userProfile=save(IUserMapper.INSTANCE.toUserProfile(model));
            registerProducer.sendNewUser(IUserMapper.INSTANCE.toRegisterElasticModel(userProfile));
            return true;
        }catch (Exception e){
            throw new UserManagerException(ErrorType.USER_NOT_CREATED);
        }

    }

    public Boolean activateStatus(String token) {
        Optional<Long>authId=jwtTokenManager.getIdFromToken(token);
        if (authId.isEmpty()){
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> userProfile=repository.findOptionalByAuthId(authId.get());
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setEStatus(EStatus.ACTIVE);
        update(userProfile.get());
        return true;
    }

    public Boolean update(UserProfileUpdateRequestDto dto){
        Optional<Long> authId = jwtTokenManager.getIdFromToken(dto.getToken());
        if (authId.isEmpty()) {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> userProfile = repository.findOptionalByAuthId(authId.get());
        if (userProfile.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        cacheManager.getCache("findbyusername").evict(userProfile.get().getUsername().toLowerCase());
        if (!dto.getUsername().equals(userProfile.get().getUsername()) || !dto.getEmail().equals(userProfile.get().getEmail())) {
            userProfile.get().setUsername(dto.getUsername());
            userProfile.get().setEmail(dto.getEmail());
            UpdateEmailOrUsernameRequestDto updateEmailOrUsernameRequestDto = IUserMapper.INSTANCE.toUpdateEmailOrUsernameRequestDto(dto);
            updateEmailOrUsernameRequestDto.setAuthId(authId.get());
            authManager.updateEmailOrUsername(updateEmailOrUsernameRequestDto);
        }
        userProfile.get().setPhone(dto.getPhone());
        userProfile.get().setAvatar(dto.getAvatar());
        userProfile.get().setAdress(dto.getAdress());
        userProfile.get().setAbout(dto.getAbout());
        update(userProfile.get());
        return true;

    }
    public Boolean delete(Long authId){
        Optional<UserProfile>userProfile=repository.findOptionalByAuthId(authId);
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setEStatus(EStatus.DELETED);
        update(userProfile.get());
        return true;
    }
     @Cacheable(value = "findbyusername",key = "#username.toLowerCase()")
    public UserProfile findByUsername(String username){
        Optional<UserProfile>userProfile=repository.findOptionalByUsernameIgnoreCase(username);
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        return userProfile.get();
    }

    @Cacheable(value = "findbyrole",key = "#role.toUpperCase()")
    public List<UserProfile> findByRole(String role){
        // ResponseEntity<List<Long>>authIds2=authManager.findByRole(role);
        List<Long>authIds= authManager.findByRole(role).getBody();//icinden gelen degeri aliyoruz getbody ile

        return authIds.stream().map(x-> repository.findOptionalByAuthId(x)
                .orElseThrow(()->{
                    throw new UserManagerException(ErrorType.USER_NOT_FOUND);
                })).collect(Collectors.toList());

    }
}
