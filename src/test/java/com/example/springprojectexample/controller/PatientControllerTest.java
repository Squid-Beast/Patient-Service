package com.example.springprojectexample.controller;

import com.example.springprojectexample.client.PatientClient;
import com.example.springprojectexample.pojo.PatientCreditDetails;
import com.example.springprojectexample.pojo.PatientDetails;
import com.example.springprojectexample.validation.PatientValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PatientValidator patientValidator;
    @MockBean
    private PatientClient patientClientMock;

    @Test
    public void testCreatePatient() throws Exception{
        PatientDetails patient = new PatientDetails();
        patient.setFirstName("Tony");
        patient.setLastName("Stark");
        patient.setGender("M");
        patient.setBirthDate(LocalDate.parse("1988-08-06"));
        patient.setCity("New York");
        patient.setProvinceId("NY");
        patient.setAllergies("HyperTension");
        patient.setHeight(176);
        patient.setWeight(80);
        patient.setSsn("123-45-7891");
        patient.setCreditScore(775);


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.lastName").exists())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        JsonNode jsonNode = objectMapper.readTree(response);

        int id = jsonNode.get("id").asInt();
        System.out.println(id);
        PatientCreditDetails dummyCreditDetails = new PatientCreditDetails();
        dummyCreditDetails.setSsn("123-45-7896");
        dummyCreditDetails.setCreditScore(752);
        Mockito.when(patientClientMock.getPatientDetails(anyLong())).thenReturn(dummyCreditDetails);
        mockMvc.perform(MockMvcRequestBuilders.get("/patient/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.lastName").exists())
                .andExpect(jsonPath("$.ssn").exists())
                .andExpect(jsonPath("$.id").value(1));

                Mockito.verify(patientClientMock,times(1)).getPatientDetails(anyLong());
    }

    @Test
    public void testGetPatient() throws Exception{

    }
}
