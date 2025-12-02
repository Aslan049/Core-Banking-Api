package com.aslankorkmaz.Core.Banking.API.exception;

import com.aslankorkmaz.Core.Banking.API.entity.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> CustomerNotFoundException(CustomerNotFoundException ex) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(false, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(CustomerAlreadyExists.class)
    public ResponseEntity<ApiResponse<Object>> CustomerAlreadyExists(CustomerAlreadyExists ex) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(false, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


}
