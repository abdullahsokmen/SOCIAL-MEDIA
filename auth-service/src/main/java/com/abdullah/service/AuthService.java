package com.abdullah.service;

import com.abdullah.dto.request.*;
import com.abdullah.dto.response.RegisterResponseDto;
import com.abdullah.exception.AuthManagerException;
import com.abdullah.exception.ErrorType;
import com.abdullah.manager.IUserManager;
import com.abdullah.mapper.IAuthMapper;
import com.abdullah.rabbitmq.producer.RegisterMailProducer;
import com.abdullah.rabbitmq.producer.RegisterProducer;
import com.abdullah.repository.IAuthRepository;
import com.abdullah.repository.entity.Auth;
import com.abdullah.repository.enums.ERole;
import com.abdullah.repository.enums.EStatus;
import com.abdullah.utility.CodeGenerator;
import com.abdullah.utility.JwtTokenManager;
import com.abdullah.utility.ServiceManager;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService extends ServiceManager<Auth,Long> {

    private final IAuthRepository authRepository;
    private final IUserManager userManager;
    private final JwtTokenManager tokenManager;
    private final CacheManager cacheManager;
    private final RegisterProducer registerProducer;
    private final RegisterMailProducer mailProducer;

    public AuthService(IAuthRepository authRepository, IUserManager userManager, JwtTokenManager tokenManager, CacheManager cacheManager, RegisterProducer registerProducer, RegisterMailProducer mailProducer){
        super(authRepository);
        this.authRepository=authRepository;
        this.userManager = userManager;
        this.tokenManager = tokenManager;
        this.cacheManager = cacheManager;
        this.registerProducer = registerProducer;
        this.mailProducer = mailProducer;
    }

    @Transactional//exception durumunda tüm islemleri geri alir bu anatosyon
    public RegisterResponseDto register(RegisterRequestDto dto) {
        Auth auth= IAuthMapper.INSTANCE.toAuth(dto);
        auth.setActivationCode(CodeGenerator.generateCode());

            try {
                save(auth);
                userManager.createUser(IAuthMapper.INSTANCE.toNewCreateUserRequestDto(auth));
                cacheManager.getCache("findbyrole").evict(auth.getRole().toString().toUpperCase());
            }catch (Exception exception){
                throw new AuthManagerException(ErrorType.BAD_REQUEST);
            }
        RegisterResponseDto registerResponseDto=IAuthMapper.INSTANCE.toRegisterResponseDto(auth);
        return registerResponseDto;
    }
    @Transactional//exception durumunda tüm islemleri geri alir bu anatosyon
    public RegisterResponseDto registerWithRabbitMq(RegisterRequestDto dto) {
        Auth auth= IAuthMapper.INSTANCE.toAuth(dto);
        auth.setActivationCode(CodeGenerator.generateCode());

        try {
            save(auth);
            //rabbitmq ile haberlesme saglanacak
            registerProducer.sendNewUser(IAuthMapper.INSTANCE.toRegisterModel(auth));
            mailProducer.sendActivationCode(IAuthMapper.INSTANCE.toRegisterMailModel(auth));
            cacheManager.getCache("findbyrole").evict(auth.getRole().toString().toUpperCase());
        }catch (Exception exception){
            throw new AuthManagerException(ErrorType.BAD_REQUEST);
        }
        RegisterResponseDto registerResponseDto=IAuthMapper.INSTANCE.toRegisterResponseDto(auth);
        return registerResponseDto;
    }

    public String login(LoginRequestDto dto) {
        Optional<Auth>auth=authRepository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (auth.isEmpty()){
            throw new AuthManagerException(ErrorType.LOGIN_ERROR);
        }
        if (!auth.get().getStatus().equals(EStatus.ACTIVE)){
            throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }
        return tokenManager.createToken(auth.get().getId(),auth.get().getRole())
                .orElseThrow(()->{throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);});
    }

    public Boolean activateStatus(ActivateRequestDto dto) {
        Optional<Auth> auth=findById(dto.getId());
        if (auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (dto.getActivationCode().equals(auth.get().getActivationCode())){
            auth.get().setStatus(EStatus.ACTIVE);
            update(auth.get());
            String token=tokenManager.createToken(auth.get().getId(),auth.get().getRole()).get();
            userManager.activateStatus("Bearer "+token);
            return true;
        }else {
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
    }

    public Boolean updateEmailOrUsername(UpdateEmailOrUsernameRequestDto dto) {
        Optional<Auth> auth=authRepository.findById(dto.getAuthId());
        if (auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setUsername(dto.getUsername());
        auth.get().setEmail(dto.getEmail());
        update(auth.get());
        return true;
    }

    @Transactional
    public Boolean delete(Long id){
        Optional<Auth>auth=findById(id);
        if (auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setStatus(EStatus.DELETED);
        update(auth.get());
        userManager.delete(id);
        return true;
    }

    public List<Long> findByRole(String role) {
        ERole myrole;
        try {
            myrole=ERole.valueOf(role.toUpperCase(Locale.ENGLISH));
        }catch (Exception e){
            throw new AuthManagerException(ErrorType.ROLE_NOT_CREATED);
        }
        return authRepository.findAllByRole(myrole).stream().map(x->x.getId()).collect(Collectors.toList());
    }
}
