package com.abdullah.controller;

import com.abdullah.dto.request.CreateFollowRequestDto;
import com.abdullah.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.abdullah.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(FOLLOW)
public class FollowController {

    private final FollowService followService;

    @PostMapping(CREATE)
    public ResponseEntity<?>createFollow(@RequestBody CreateFollowRequestDto dto){

        return ResponseEntity.ok(followService.createFollow(dto));

    }
}
