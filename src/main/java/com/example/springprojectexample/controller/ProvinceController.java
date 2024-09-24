package com.example.springprojectexample.controller;
import com.example.springprojectexample.pojo.ProvinceDetails;
import com.example.springprojectexample.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    @GetMapping("/province/{id}")
    public ProvinceDetails getProvinceId(@PathVariable("id")String provinceId){
        return provinceService.getProvinceDetails(provinceId);
    }

}
