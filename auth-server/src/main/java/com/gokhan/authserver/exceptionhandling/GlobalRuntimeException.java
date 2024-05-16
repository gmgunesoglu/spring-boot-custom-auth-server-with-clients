package com.gokhan.authserver.exceptionhandling;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class GlobalRuntimeException extends RuntimeException{
    private final String message;
    private final HttpStatus httpStatus;

    public GlobalResponseEntity toGeneralResponseEntity(){
        return new GlobalResponseEntity(this.httpStatus.value(),this.message,System.currentTimeMillis());
    }
}