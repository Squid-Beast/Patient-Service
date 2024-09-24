package com.example.springprojectexample.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientError {
    private String message;
    private Long errorCode;

    public PatientError(String message, Long errorCode){
        this.message = message;
        this.errorCode = errorCode;
    }
}