package com.abdullah.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewPostRequestDto {
    private String userId;
    private String username;
    private String title;
    private String content;
    private String mediaUrl;
}
