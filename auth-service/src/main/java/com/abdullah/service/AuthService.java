package com.abdullah.service;

import com.abdullah.constant.ApiUrls;
import com.abdullah.dto.request.ActivateRequestDto;
import com.abdullah.dto.request.LoginRequestDto;
import com.abdullah.dto.request.RegisterRequestDto;
import com.abdullah.dto.request.UpdateEmailOrUsernameRequestDto;
import com.abdullah.dto.response.RegisterResponseDto;
import com.abdullah.exception.AuthManagerException;
import com.abdullah.exception.ErrorType;
import com.abdullah.manager.IUserManager;
import com.abdullah.mapper.IAuthMapper;
import com.abdullah.repository.IAuthRepository;
import com.abdullah.repository.entity.Auth;
import com.abdullah.repository.enums.EStatus;
import com.abdullah.utility.CodeGenerator;
import com.abdullah.utility.JwtTokenManager;
import com.abdullah.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {

    private final IAuthRepository authRepository;
    private final IUserManager userManager;
    private final JwtTokenManager tokenManager;

    public AuthService(IAuthRepository authRepository, IUserManager userManager, JwtTokenManager tokenManager){
        super(authRepository);
        this.authRepository=authRepository;
        this.userManager = userManager;
        this.tokenManager = tokenManager;
    }

    public RegisterResponseDto register(RegisterRequestDto dto) {
        Auth auth= IAuthMapper.INSTANCE.toAuth(dto);
        auth.setActivationCode(CodeGenerator.generateCode());
            save(auth);
        userManager.createUser(IAuthMapper.INSTANCE.toNewCreateUserRequestDto(auth));
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
            userManager.activateStatus(auth.get().getId());
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

}
