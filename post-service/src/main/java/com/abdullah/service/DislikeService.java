package com.abdullah.service;

import com.abdullah.repository.IDislikeRepository;
import com.abdullah.repository.ILikeRepository;
import com.abdullah.repository.entity.Dislike;
import com.abdullah.repository.entity.Like;
import com.abdullah.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class DislikeService extends ServiceManager<Dislike,String> {

    private final IDislikeRepository dislikeRepository;


    public DislikeService(IDislikeRepository dislikeRepository) {
        super(dislikeRepository);
        this.dislikeRepository = dislikeRepository;
    }
}
