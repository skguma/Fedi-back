package com.fedi.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class FileUploadExceptionAdvice {
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private String illegalArgumentHandler(RuntimeException e){
        return "{\"error\":\""+e.getMessage()+"\"}";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    private String fileSizeLimitExceededHandler(MaxUploadSizeExceededException e){
        return "{\"error\":\"file too big\"}";
    }
}
