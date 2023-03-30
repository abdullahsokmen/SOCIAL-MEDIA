package com.abdullah.service;

import com.abdullah.repository.ILikeRepository;
import com.abdullah.repository.entity.Like;
import com.abdullah.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class LikeService extends ServiceManager<Like,String> {

    private final ILikeRepository likeRepository;


    public LikeService(ILikeRepository likeRepository) {
        super(likeRepository);
        this.likeRepository = likeRepository;
    }
}
