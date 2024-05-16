package com.gokhan.authserver.exceptionhandling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GlobalResponseEntity {
    private int status;
    private String message;
    private long timeStamp;

    @Override
    public String toString() {
        return "PersonErrorResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
