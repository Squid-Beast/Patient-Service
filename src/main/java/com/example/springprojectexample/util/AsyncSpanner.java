package com.example.springprojectexample.util;

import com.example.springprojectexample.client.PatientClient;
import com.example.springprojectexample.config.SpringConfig;
import com.example.springprojectexample.pojo.PatientCreditDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class AsyncSpanner {
    @Autowired
    private PatientClient patientClient;

    //Executes Asynchronously and stores in a Future object. It waits till the end for the task to complete.
    //Main thread continues the execution and Async task will be executed in a separate thread.
    @Async
    public Future<PatientCreditDetails> getPatientDetails(Long patientId){
        PatientCreditDetails details = patientClient.getPatientDetails(patientId);
        return new AsyncResult<>(details);
    }
}
