package com.example.springprojectexample.controller;

import com.example.springprojectexample.aop.LogExecutionTime;
import com.example.springprojectexample.pojo.AdmissionDetails;
import com.example.springprojectexample.service.AdmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdmissionController {

    @Autowired
    private AdmissionService admissionService;

    @PostMapping("/admission")
    public AdmissionDetails addNewAdmission(@RequestBody final AdmissionDetails admissionDetails) {
        return admissionService.addNewAdmission(admissionDetails);
    }

    @GetMapping("/admission/patient/{id}")
    @LogExecutionTime
    public List<AdmissionDetails> getAdmissionsDetailsByPatientId(@PathVariable("id") Long patientId) {
        return admissionService.getAdmissionsDetailsByPatientId(patientId);
    }

    @DeleteMapping("/admission")
    public String deleteAdmissionDetailsByPatientId(@RequestParam("patient_id") Long patientId) {
        admissionService.deleteAdmissionByPatientId(patientId);
        return "Record Deleted Successfully";

    }
}
