package com.example.springprojectexample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class PatientExceptionHandler {

    @ExceptionHandler(PatientException.class)
    public ResponseEntity<?> handlePatientException(PatientException e){
        return null;
    }

//    @ExceptionHandler(HttpServerErrorException.class)
//    public ResponseEntity<?> handleHttpServerException(HttpServerErrorException e){
//        e.printStackTrace();
//        return new ResponseEntity<>(new PatientError("Web Request Not Processed", 999L),HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRunTimeException(RuntimeException e){
        return new ResponseEntity<>(new PatientError(e.getMessage(),400L), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception e){
        //This will catch any other exceptions which are not handled by specific exception handlers
        e.printStackTrace();
        return new ResponseEntity<>(new PatientError("Patient Details Error",99999L), HttpStatus.NOT_FOUND);
    }
}
