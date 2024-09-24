package com.example.springprojectexample.service;

import com.example.springprojectexample.entity.Province;
import com.example.springprojectexample.pojo.ProvinceDetails;
import com.example.springprojectexample.repository.PatientRepository;
import com.example.springprojectexample.repository.ProvinceRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinceService {
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private PatientRepository patientRepository;

    public ProvinceDetails getProvinceDetails(String provinceId){
        Province provinceById = provinceRepository.findById(provinceId).orElse(null);
        if (provinceId == null){
            throw new RuntimeException("No Record");
        }
        ProvinceDetails provinceDetails= new ProvinceDetails();
        BeanUtils.copyProperties(provinceById,provinceDetails);
        return provinceDetails;
    }

}
