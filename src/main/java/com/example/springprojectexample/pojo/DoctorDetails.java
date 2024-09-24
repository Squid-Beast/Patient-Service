package com.example.springprojectexample.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorDetails {
    private Long id;
    private String firstName;
    private String lastName;
    private String specialty;
    private List<AdmissionDetails> admissionDetailsList;
}
