package com.example.springprojectexample.service;

import com.example.springprojectexample.aop.LogExecutionTime;
import com.example.springprojectexample.client.PatientClient;
import com.example.springprojectexample.entity.Patient;
import com.example.springprojectexample.pojo.AdmissionDetails;
import com.example.springprojectexample.pojo.PatientCreditDetails;
import com.example.springprojectexample.pojo.PatientDetails;
import com.example.springprojectexample.repository.PatientRepository;
import com.example.springprojectexample.util.AsyncSpanner;
import com.example.springprojectexample.validation.PatientValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;        //Controller -> when url hits it goes to controller.
    @Autowired
    private AdmissionService admissionService;
    @Autowired
    private PatientClient patientClient;
    @Autowired
    private PatientValidator patientValidator;
    @Autowired
    private AsyncSpanner asyncSpanner;

    @LogExecutionTime
    public PatientDetails createPatient(PatientDetails patient) {
        patientValidator.validatePatientRequest(patient);   //Need to validate before inserting the request data

        Patient newPatient = new Patient();
        newPatient.setFirstName(patient.getFirstName());
        newPatient.setLastName(patient.getLastName());
        newPatient.setGender(patient.getGender());
        newPatient.setBirthDate(patient.getBirthDate());
        newPatient.setCity(patient.getCity());
        newPatient.setProvinceId(patient.getProvinceId());
        newPatient.setAllergies(patient.getAllergies());
        newPatient.setHeight(patient.getHeight());
        newPatient.setWeight(patient.getWeight());

        Patient savedPatient = patientRepository.save(newPatient);  //Saving Created data to Repository

        PatientCreditDetails newPatientDetails = new PatientCreditDetails();
        newPatientDetails.setId(savedPatient.getId());
        newPatientDetails.setSsn(patient.getSsn());
        newPatientDetails.setCreditScore(patient.getCreditScore());

        patientClient.createPatientDetails(newPatientDetails);
        PatientDetails patientDetails=new PatientDetails();
        BeanUtils.copyProperties(savedPatient,patientDetails);
        patientDetails.setSsn(newPatientDetails.getSsn());
        patientDetails.setCreditScore(newPatientDetails.getCreditScore());
        return patientDetails;
    }

    @LogExecutionTime
    public PatientDetails getPatient(Long id) throws ExecutionException, InterruptedException {
        Patient patientById = patientRepository.findById(id).orElse(null);  //Connect to repository to get details.
        if (patientById == null) {
            throw new RuntimeException("No Patient Found.");        //If id not found throw exception.
        }
        Future<PatientCreditDetails> detailsFuture = asyncSpanner.getPatientDetails(id);

        PatientDetails patientDetails = new PatientDetails();     //Copying Entity properties to Pojo
        BeanUtils.copyProperties(patientById, patientDetails);

        List<AdmissionDetails> admissionsDetailsByPatientIdList = admissionService.getAdmissionsDetailsByPatientId(id);
        //Set Admission details list in pojo with  admission details by patient id list
        patientDetails.setAdmissionDetailsList(admissionsDetailsByPatientIdList);
        PatientCreditDetails creditDetails = detailsFuture.get();
        patientDetails.setSsn(creditDetails.getSsn());
        patientDetails.setCreditScore(creditDetails.getCreditScore());

        return patientDetails;          //We can't return entity so, we create new pojo object and return that.
    }

    @LogExecutionTime
    public List<PatientDetails> getPatientList(){
        List<Patient> patientList = patientRepository.findAll();
        List<PatientDetails> patientDetailsList = new ArrayList<>();
//        for(Patient patient: patientList){
            patientList.stream().parallel().forEach(patient -> {
                PatientDetails patientDetails= new PatientDetails();
                BeanUtils.copyProperties(patient,patientDetails);
                PatientCreditDetails patientCreditDetails = patientClient.getPatientDetails(patient.getId());
                if (patientCreditDetails!=null) {
                    patientDetails.setSsn(patientCreditDetails.getSsn());
                    patientDetails.setCreditScore(patientCreditDetails.getCreditScore());
                }
                patientDetailsList.add(patientDetails);
            });
//        }
        exportPatientList(patientDetailsList);
        return patientDetailsList;
    }

    public String exportPatientList(List<PatientDetails> patientDetailsList){
        ObjectMapper objectMapper= new ObjectMapper();
        try {
            objectMapper.registerModule(new JavaTimeModule());
            String jsonFile = objectMapper.writeValueAsString(patientDetailsList);

            FileWriter fileWriter = new FileWriter("output.json");
            fileWriter.write(jsonFile);
            fileWriter.close();
            return "Data added to file.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred.";
        }
    }

}
