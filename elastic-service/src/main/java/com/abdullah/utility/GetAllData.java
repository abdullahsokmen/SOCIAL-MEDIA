package com.abdullah.utility;

import com.abdullah.manager.IUserManager;
import com.abdullah.repository.entity.UserProfile;
import com.abdullah.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllData {

    private final UserProfileService userProfileService;
    private final IUserManager userManager;

    //z@PostConstruct
    public void initData(){
        List<UserProfile> userProfileList=userManager.findAll().getBody();
        userProfileService.saveAll(userProfileList);
    }
}
