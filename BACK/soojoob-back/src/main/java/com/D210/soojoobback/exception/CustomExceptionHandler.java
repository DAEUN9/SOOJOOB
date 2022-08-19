package com.D210.soojoobback.exception;

import com.D210.soojoobback.dto.user.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = {CustomErrorException.class})
    public ResponseEntity<Object> handleApiRequestException(RuntimeException ex) {
        ResponseDto restApiException = new ResponseDto(400L, ex.getMessage(),"");

        return new ResponseEntity<>(
                restApiException,
                HttpStatus.BAD_REQUEST
        );
    }
}
