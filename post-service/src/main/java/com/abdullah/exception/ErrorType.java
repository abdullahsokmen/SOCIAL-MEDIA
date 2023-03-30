package com.abdullah.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@Getter
public enum ErrorType {
    INTERNAL_ERROR(5300,"Sunucu hatası",HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4300,"Parametre hatası",HttpStatus.BAD_REQUEST),

    USER_NOT_FOUND(4310,"Böyle bir kullanıcı bulunamadı",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4311,"GECERSİZ TOKEN",HttpStatus.BAD_REQUEST),
    ;

    private int code;

    private String message;
    HttpStatus httpStatus;
}
