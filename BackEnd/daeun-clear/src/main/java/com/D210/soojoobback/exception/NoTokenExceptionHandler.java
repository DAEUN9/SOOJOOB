package com.D210.soojoobback.exception;

import com.D210.soojoobback.dto.user.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NoTokenExceptionHandler {
    @ExceptionHandler(value = {NoTokenException.class})
    public ResponseEntity<Object> handleApiRequestException(RuntimeException ex) {
        ResponseDto restApiException = new ResponseDto(401L, ex.getMessage(),"");

        return new ResponseEntity<>(
                restApiException,
                HttpStatus.UNAUTHORIZED
        );
    }
}

