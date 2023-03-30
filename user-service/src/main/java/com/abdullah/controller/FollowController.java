package com.abdullah.controller;

import com.abdullah.dto.request.CreateFollowRequestDto;
import com.abdullah.repository.entity.Follow;
import com.abdullah.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping(FINDALL)
    public ResponseEntity<List<Follow>>findAll(){
        return ResponseEntity.ok(followService.findAll());
    }
}
