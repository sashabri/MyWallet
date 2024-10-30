package com.example.mywallet.advice;

import com.example.mywallet.controller.entity.ErrorResponse;
import com.example.mywallet.exception.InvalidInputDataException;
import com.example.mywallet.exception.NotEnoughMoneyException;
import com.example.mywallet.exception.WalletNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(InvalidInputDataException.class)
    public ResponseEntity<ErrorResponse> invalidInputDataHandler(InvalidInputDataException e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.toString(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<ErrorResponse> notEnoughMoneyHandler(NotEnoughMoneyException e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.toString(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<ErrorResponse> walletNotFoundHandler(WalletNotFoundException e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.toString(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST
        );
    }
}
