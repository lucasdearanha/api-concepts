package com.example.demo.exceptions.globalhandler;

import com.example.demo.exceptions.DatabaseException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.reponsemodels.GenericResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private final GenericResponseModel error = new GenericResponseModel(false, null, null);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GenericResponseModel> handleNotFoundException(NotFoundException e,
                                                                        HttpServletRequest request) {
        error.setMessage(e.getMessage());
        error.setData(request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<GenericResponseModel> handleDatabaseException(DatabaseException e,
                                                                        HttpServletRequest request) {
        error.setMessage(e.getMessage());
        error.setData(request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
