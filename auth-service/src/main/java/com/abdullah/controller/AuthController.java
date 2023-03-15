package com.abdullah.controller;

import com.abdullah.dto.request.ActivateRequestDto;
import com.abdullah.dto.request.LoginRequestDto;
import com.abdullah.dto.request.RegisterRequestDto;
import com.abdullah.dto.response.RegisterResponseDto;
import com.abdullah.repository.entity.Auth;
import com.abdullah.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.abdullah.constant.ApiUrls.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {
    private final AuthService authService;

    @PostMapping(REGISTER)
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto){
            return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping(ACTIVATESTATUS)
    public ResponseEntity<Boolean>activateStatus(@RequestBody ActivateRequestDto dto){
        return ResponseEntity.ok(authService.activateStatus(dto));
    }

    @GetMapping(FINDALL)
    public ResponseEntity<List<Auth>>findAll(){
        return ResponseEntity.ok(authService.findAll());
    }
}
