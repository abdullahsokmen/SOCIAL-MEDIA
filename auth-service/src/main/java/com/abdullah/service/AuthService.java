package com.abdullah.service;

import com.abdullah.dto.request.ActivateRequestDto;
import com.abdullah.dto.request.LoginRequestDto;
import com.abdullah.dto.request.RegisterRequestDto;
import com.abdullah.dto.response.RegisterResponseDto;
import com.abdullah.exception.AuthManagerException;
import com.abdullah.exception.ErrorType;
import com.abdullah.manager.IUserManager;
import com.abdullah.mapper.IAuthMapper;
import com.abdullah.repository.IAuthRepository;
import com.abdullah.repository.entity.Auth;
import com.abdullah.repository.enums.EStatus;
import com.abdullah.utility.CodeGenerator;
import com.abdullah.utility.ServiceManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {

    private final IAuthRepository authRepository;
    private final IUserManager userManager;

    public AuthService(IAuthRepository authRepository, IUserManager userManager){
        super(authRepository);
        this.authRepository=authRepository;
        this.userManager = userManager;
    }

    public RegisterResponseDto register(RegisterRequestDto dto) {
        Auth auth= IAuthMapper.INSTANCE.toAuth(dto);
        auth.setActivationCode(CodeGenerator.generateCode());
            save(auth);
        userManager.createUser(IAuthMapper.INSTANCE.toNewCreateUserRequestDto(auth));
        RegisterResponseDto registerResponseDto=IAuthMapper.INSTANCE.toRegisterResponseDto(auth);
        return registerResponseDto;
    }

    public Boolean login(LoginRequestDto dto) {
        Optional<Auth>auth=authRepository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (auth.isEmpty()){
            throw new AuthManagerException(ErrorType.LOGIN_ERROR);
        }
        return true;
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
}
