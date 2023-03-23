package com.abdullah.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileUpdateRequestDto {
    private String token;

    private String email;
    private String username;
    private String phone;
    private String avatar;
    private String adress;
    private String about;
}
