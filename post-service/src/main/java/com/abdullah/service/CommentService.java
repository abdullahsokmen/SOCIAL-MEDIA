package com.abdullah.service;

import com.abdullah.repository.ICommentRepository;
import com.abdullah.repository.entity.Comment;
import com.abdullah.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends ServiceManager<Comment,String> {

    private final ICommentRepository commentRepository;


    public CommentService(ICommentRepository commentRepository) {
        super(commentRepository);
        this.commentRepository = commentRepository;
    }
}
