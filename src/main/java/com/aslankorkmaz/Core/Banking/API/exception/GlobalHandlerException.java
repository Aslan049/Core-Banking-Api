package com.aslankorkmaz.Core.Banking.API.exception;

import com.aslankorkmaz.Core.Banking.API.entity.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalHandlerException {

    //404: Customer Not Found
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomerNotFound(CustomerNotFoundException ex) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(false, ex.getMessage(), null, "CUST-404");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    //404: Account Not Found
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomerAlreadyExists(AccountNotFoundException ex) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(false, ex.getMessage(), null,"ACC-404");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    //409: Conflict
    @ExceptionHandler(CustomerAlreadyExists.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomerAlreadyExists(CustomerAlreadyExists ex) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(false, ex.getMessage(), null,"CUST-409");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    //409: Conflict
    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccountAlreadyExists(AccountAlreadyExistsException ex) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(false, ex.getMessage(), null,"ACC-409");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    //400: Bad Request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequest(BadRequestException ex) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(false, ex.getMessage(), null,"VALID-400");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }


    //400: Insufficient Funds
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ApiResponse<Object>> handleInsufficientFunds(InsufficientFundsException ex) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(false, ex.getMessage(), null,"FUNDS-400");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }


}
