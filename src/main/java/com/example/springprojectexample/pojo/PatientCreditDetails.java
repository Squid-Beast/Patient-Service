package com.example.springprojectexample.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PatientCreditDetails {
    private Long id;
    private String ssn;
    private int creditScore;
}
