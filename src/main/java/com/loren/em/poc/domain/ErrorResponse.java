package com.loren.em.poc.domain;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {

    private HttpStatus httpStatus;
    private String errorMsg;

}
