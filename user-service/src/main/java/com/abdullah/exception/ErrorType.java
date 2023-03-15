package com.abdullah.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@Getter
public enum ErrorType {
    INTERNAL_ERROR(5200,"Sunucu hatası",HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4200,"Parametre hatası",HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATE(4210,"Böyle bir kullanıcı adı mevcuttur",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4211,"Böyle bir kullanıcı bulunamadı",HttpStatus.BAD_REQUEST),
    USER_NOT_CREATED(4212,"Kullanıcı oluşturulamadı",HttpStatus.BAD_REQUEST)
    ;

    private int code;

    private String message;
    HttpStatus httpStatus;
}
