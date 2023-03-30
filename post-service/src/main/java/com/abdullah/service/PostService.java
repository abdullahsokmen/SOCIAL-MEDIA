package com.abdullah.service;

import com.abdullah.dto.request.CreateNewPostRequestDto;
import com.abdullah.mapper.IPostMapper;
import com.abdullah.repository.IPostRepository;
import com.abdullah.repository.entity.Post;
import com.abdullah.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class PostService extends ServiceManager<Post,String> {

    private final IPostRepository postRepository;

    public PostService(IPostRepository postRepository) {
        super(postRepository);
        this.postRepository = postRepository;
    }

    public Post createPost(CreateNewPostRequestDto dto) {

        return save(IPostMapper.INSTANCE.toPost(dto));

    }
}
