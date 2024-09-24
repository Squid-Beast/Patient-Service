package com.example.springprojectexample.client;

import com.example.springprojectexample.pojo.PatientCreditDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PatientClient {
    @Autowired
    private RestTemplate restTemplate;

    public PatientCreditDetails getPatientDetails(Long patientId) {
        try {
            ResponseEntity<PatientCreditDetails> responseEntity = restTemplate.exchange(buildUrl(patientId), HttpMethod.GET, buildHttpEntity(), PatientCreditDetails.class);
            HttpStatus statusCode = responseEntity.getStatusCode();
            if (statusCode != HttpStatus.OK) {
                return null;
            }
            System.out.println(statusCode);
            return responseEntity.getBody();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public void createPatientDetails(PatientCreditDetails patientCreditDetails){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity<PatientCreditDetails> httpEntity = new HttpEntity<>(patientCreditDetails, headers);
        ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:8081/patient", HttpMethod.POST, httpEntity, Void.class);
        if (responseEntity.getStatusCodeValue()!=204){
            throw new RuntimeException("Patient Creation Failed.");
        }
        HttpStatus statusCode = responseEntity.getStatusCode();
        System.out.println(statusCode);

    }

    private HttpEntity buildHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        return new HttpEntity<>(headers);
    }

    private String buildUrl(Long patientId){
        String url = "http://localhost:8081/patient/"+patientId;
        return url;
    }
}
