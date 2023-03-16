package com.abdullah.controller;

import com.abdullah.dto.request.NewCreateUserRequestDto;
import com.abdullah.dto.request.UserProfileUpdateRequestDto;
import com.abdullah.repository.entity.UserProfile;
import com.abdullah.service.UserProfileService;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.abdullah.constant.ApiUrls.*;
@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;


    @PostMapping(CREATE)
    public ResponseEntity<Boolean>createUser(@RequestBody NewCreateUserRequestDto dto){
        return ResponseEntity.ok(userProfileService.createUser(dto));
    }

    @GetMapping(ACTIVATESTATUS+"/{authId}")
    public ResponseEntity<Boolean>activateStatus(@PathVariable Long authId){
        return ResponseEntity.ok(userProfileService.activateStatus(authId));
    }

    @PutMapping(UPDATE)
    public ResponseEntity<Boolean>update(@RequestBody UserProfileUpdateRequestDto dto){
        return ResponseEntity.ok(userProfileService.update(dto));
    }


}
