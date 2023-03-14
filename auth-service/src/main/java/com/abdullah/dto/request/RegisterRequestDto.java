package com.abdullah.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
    @NotBlank
    @Size(min = 3,max = 20,message = "Kullanıcı adı en az 3 en fazla 20 karakter uzunluğunda olmalıdır")
    private String username;
    @Email
    private String email;
    @NotBlank
    @Size(min = 8,max = 32,message = "Sifre en az 5 en fazla 20 karakter uzunluğunda olmalıdır")
    private String password;
}
