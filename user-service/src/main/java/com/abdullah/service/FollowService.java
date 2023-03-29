package com.abdullah.service;

import com.abdullah.dto.request.CreateFollowRequestDto;
import com.abdullah.exception.ErrorType;
import com.abdullah.exception.UserManagerException;
import com.abdullah.config.mapper.IFollowMapper;
import com.abdullah.repository.IFollowRepository;
import com.abdullah.repository.entity.Follow;
import com.abdullah.repository.entity.UserProfile;
import com.abdullah.utility.JwtTokenManager;
import com.abdullah.utility.ServiceManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FollowService extends ServiceManager<Follow,String> {

    private final IFollowRepository followRepository;

    private final JwtTokenManager jwtTokenManager;

    private final UserProfileService userProfileService;

    public FollowService(IFollowRepository followRepository, JwtTokenManager jwtTokenManager, UserProfileService userProfileService) {
        super(followRepository);
        this.followRepository = followRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.userProfileService = userProfileService;
    }

    @Transactional
    public Boolean createFollow(CreateFollowRequestDto dto) {
        Follow follow;
        Optional<Long>authId=jwtTokenManager.getIdFromToken(dto.getToken());
        if (authId.isEmpty()){
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile>userProfile=userProfileService.findByAuthId(authId.get());
        Optional<UserProfile>followUser=userProfileService.findById(dto.getFollowId());

        Optional<Follow> followDb=followRepository.findOptionalByUserIdAndFollowId(userProfile.get().getId(),
                followUser.get().getId());

        if (followDb.isPresent()){
            throw new UserManagerException(ErrorType.FOLLOW_ALREADY_EXIST);
        }

        if (userProfile.isPresent()&&followUser.isPresent()){
           follow= save(IFollowMapper.INSTANCE.toFollow(dto,userProfile.get().getId()));
           userProfile.get().getFollows().add(followUser.get().getId());
           followUser.get().getFollower().add(userProfile.get().getId());
           userProfileService.update(userProfile.get());
           userProfileService.update(followUser.get());
        }else {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        return true;
    }
}
