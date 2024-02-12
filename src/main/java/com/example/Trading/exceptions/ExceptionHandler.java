package com.example.Trading.exceptions;

import com.example.Trading.model.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleEmpDetailsExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleUserNotFound(Exception exception, WebRequest res) {
        ExceptionModel ex = new ExceptionModel(HttpStatus.NOT_FOUND.toString(), exception.getMessage(), res.getDescription(false));
        return new ResponseEntity<Object>(ex, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({AlreadyExists.class})
    public ResponseEntity<Object> handleUserAlreadyExists(Exception exception, WebRequest res) {
        ExceptionModel ex = new ExceptionModel(HttpStatus.CONFLICT.toString(), exception.getMessage(), res.getDescription(false));
        return new ResponseEntity<Object>(ex, HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({UserNotExists.class})
    public ResponseEntity<Object> handleUserNotExists(Exception exception, WebRequest res) {
        ExceptionModel ex = new ExceptionModel(HttpStatus.NOT_FOUND.toString(), exception.getMessage(), res.getDescription(false));
        return new ResponseEntity<Object>(ex, HttpStatus.NOT_FOUND);
    }
//    @org.springframework.web.bind.annotation.ExceptionHandler({ContactNotUpdated.class})
//    public ResponseEntity<Object> handleContactNotUpdated(Exception exception, WebRequest res) {
//        ExceptionModel ex = new ExceptionModel(HttpStatus.OK.toString(), exception.getMessage(), res.getDescription(false));
//        return new ResponseEntity<Object>(ex, HttpStatus.OK);
//    }


}

