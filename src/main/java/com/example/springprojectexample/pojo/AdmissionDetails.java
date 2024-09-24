package com.example.springprojectexample.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdmissionDetails {
    private Long id;
    private LocalDate admissionDate;
    private LocalDate dischargeDate;
    private String diagnosis;
    private Long doctorId;

    private PatientDetails patientDetails;
    private Long patientId;
}
