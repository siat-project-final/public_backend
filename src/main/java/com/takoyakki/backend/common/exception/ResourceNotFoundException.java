package com.takoyakki.backend.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResourceNotFoundException extends RuntimeException{
    private final HttpStatus status;
    private final String message;

    public ResourceNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
        this.message = message;
    }
}
