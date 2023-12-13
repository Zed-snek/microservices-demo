package com.keymarket.order_service.exception;

import com.keymarket.order_service.response.MyErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MyErrorResponse> entityHandler(EntityNotFoundException e) {

        var errorResponse = new MyErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND); //404
    }

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<MyErrorResponse> businessLogicHandler(BusinessLogicException e) {

        var errorResponse = new MyErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); //400
    }

}
