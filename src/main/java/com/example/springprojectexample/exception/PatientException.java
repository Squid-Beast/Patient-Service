package com.example.springprojectexample.exception;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public class PatientException extends RuntimeException{
    private String message;
    private Exception e;

    public PatientException(String message, Exception e){
        super(message);
        this.message=message;
        this.e=e;
        log.error("Application Exception: {}", message, e);
    }
}
