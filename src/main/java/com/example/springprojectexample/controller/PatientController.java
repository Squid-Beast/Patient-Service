package com.example.springprojectexample.controller;

import com.example.springprojectexample.aop.LogExecutionTime;
import com.example.springprojectexample.pojo.PatientDetails;
import com.example.springprojectexample.service.PatientService;
import com.example.springprojectexample.validation.PatientValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Log4j2
@RestController
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientValidator patientValidator;

    @GetMapping("/patient/{id}")
    @LogExecutionTime
    public PatientDetails getPatient(@PathVariable("id") Long id) throws ExecutionException, InterruptedException {
        log.info("Patient Record Found: {}", id);
        return patientService.getPatient(id);
    }

    @GetMapping("/patient/list/export")
    public String exportPatientListToFile(){
        List<PatientDetails> patientDetailsList = patientService.getPatientList();
        return patientService.exportPatientList(patientDetailsList);
    }

    @GetMapping("/patient/list")
    public List<PatientDetails> getPatientList(){
        return patientService.getPatientList();
    }

    @DeleteMapping("/patient")
    public String deletePatient(@RequestParam("id") int id) {
        System.out.println(id);
        return "Record Deleted";            //url: /patient?id=110
    }

    @PostMapping("/patient")
    @LogExecutionTime
    public PatientDetails createPatient(@RequestBody PatientDetails patient) {
        log.info("New Patient Details: {}", patient);
        return patientService.createPatient(patient);
    }

    @PutMapping("/patient/{id}")
    public PatientDetails updatePatient(@PathVariable("id") int id, @RequestBody PatientDetails patient) {
        return patient;
    }
}