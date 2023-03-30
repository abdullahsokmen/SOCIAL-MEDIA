package com.abdullah.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity{

    @Id
    private String id;
    private String userId;
    private String username;
    private String postId;
    private String content;
    private int like;
    private int dislike;
}
