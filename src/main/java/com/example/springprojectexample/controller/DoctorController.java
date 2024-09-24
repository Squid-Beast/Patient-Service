package com.example.springprojectexample.controller;

import com.example.springprojectexample.pojo.DoctorDetails;
import com.example.springprojectexample.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

//    @GetMapping("/doctor/{id}")
//    public DoctorDetails getDoctorDetailsById(@PathVariable("id") Long doctorId) {
//        return doctorService.getDoctorDetailsById(doctorId);
//    }

    @GetMapping("/doctor/admission/{id}")
    public DoctorDetails getAdmissionsByDoctorId(@PathVariable("id") Long doctorId) {
        return doctorService.getDoctorDetailsById(doctorId);
    }

    @DeleteMapping("/doctor/{id}")
    public String deleteDoctorById(@PathVariable("id") Long doctorId) {
        doctorService.deleteDoctorById(doctorId);
        return "Record Deleted";
    }

    @PutMapping("/doctor/{id}")
    public DoctorDetails createNewDoctor(@RequestParam("id") Long doctorId, @RequestBody DoctorDetails newDoctor) {
        return doctorService.updateDoctor(newDoctor);
    }
}
