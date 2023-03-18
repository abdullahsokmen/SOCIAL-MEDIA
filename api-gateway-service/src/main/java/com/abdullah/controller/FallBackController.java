package com.abdullah.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallBackController {

    @GetMapping("/authservice")
    public ResponseEntity<String>authServiceFallback(){
        return ResponseEntity.ok("Auth service su anda hizmet verememektedir.");
    }

    @GetMapping("/userservice")
    public ResponseEntity<String>userServiceFallback(){
        return ResponseEntity.ok("User service su anda hizmet verememektedir.");
    }
}
