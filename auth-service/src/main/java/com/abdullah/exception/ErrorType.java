package com.abdullah.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@Getter
public enum ErrorType {
    INTERNAL_ERROR(5100,"Sunucu hatası",HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4100,"Parametre hatası",HttpStatus.BAD_REQUEST),
    LOGIN_ERROR(4110,"Kullanıcı adı veya şifre hatalı!",HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATE(4111,"Böyle bir kullanıcı adı mevcuttur",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4112,"Böyle bir kullanıcı bulunamadı",HttpStatus.BAD_REQUEST),
    ACTIVATE_CODE_ERROR(4113,"Aktivasyon kodu hatası",HttpStatus.BAD_REQUEST)

    ;

    private int code;
    private String message;
    HttpStatus httpStatus;
}
