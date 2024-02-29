package com.loren.em.poc.exception;

import com.loren.em.poc.domain.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmBadRequestException.class)
    public ErrorResponse emBadRequestExceptionHandler(EmBadRequestException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setErrorMsg(errorResponse.getErrorMsg());
        return errorResponse;
    }

}
