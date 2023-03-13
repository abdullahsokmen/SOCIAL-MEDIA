package com.abdullah.service;

import com.abdullah.dto.request.RegisterRequestDto;
import com.abdullah.dto.response.RegisterResponseDto;
import com.abdullah.mapper.IAuthMapper;
import com.abdullah.repository.IAuthRepository;
import com.abdullah.repository.entity.Auth;
import com.abdullah.utility.CodeGenerator;
import com.abdullah.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class AuthService extends ServiceManager<Auth,Long> {

    private final IAuthRepository authRepository;

    public AuthService(IAuthRepository authRepository){
        super(authRepository);
        this.authRepository=authRepository;
    }

    public RegisterResponseDto register(RegisterRequestDto dto) {
        Auth auth= IAuthMapper.INSTANCE.toAuth(dto);
        auth.setActivationCode(CodeGenerator.generateCode());
        save(auth);
        RegisterResponseDto registerResponseDto=IAuthMapper.INSTANCE.toRegisterResponseDto(auth);
        return registerResponseDto;
    }
}
