package com.abdullah.manager;

import com.abdullah.dto.request.UpdateEmailOrUsernameRequestDto;
import com.abdullah.dto.request.UserProfileUpdateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "userprofile-auth",url = "http://localhost:7071/api/v1/auth",decode404 = true)
public interface IAuthManager {

    @PutMapping("/updateemailorusername")
    public ResponseEntity<Boolean>updateEmailOrUsername(@RequestBody UpdateEmailOrUsernameRequestDto dto);


}
