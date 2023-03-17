package com.abdullah.manager;

import com.abdullah.dto.request.DeleteUserProfileRequestDto;
import com.abdullah.dto.request.NewCreateUserRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.abdullah.constant.ApiUrls.ACTIVATESTATUS;
import static com.abdullah.constant.ApiUrls.DELETEBYID;


@FeignClient(url = "http://localhost:7072/api/v1/user",decode404 = true,name = "auth-userprofile")
public interface IUserManager {

    @PostMapping("/create")
    public ResponseEntity<Boolean> createUser(@RequestBody NewCreateUserRequestDto dto);

    @GetMapping(ACTIVATESTATUS+"/{authId}")
    public ResponseEntity<Boolean>activateStatus(@PathVariable Long authId);

    @DeleteMapping(DELETEBYID)
    public ResponseEntity<Boolean>delete(@RequestParam Long authId);


}
