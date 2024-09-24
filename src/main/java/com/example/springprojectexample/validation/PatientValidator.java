package com.example.springprojectexample.validation;
import com.example.springprojectexample.pojo.PatientDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class PatientValidator {
    public void validatePatientRequest(PatientDetails patientDetails){
        if (StringUtils.isEmpty(patientDetails.getFirstName())){
            throw new RuntimeException("First Name Should not be null");
        }
        if (StringUtils.isEmpty(patientDetails.getLastName())){
            throw new RuntimeException("Last Name Should not be null");
        }

//        LocalDate birthDate = patientDetails.getBirthDate();
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
//        String dateFormat = "\\d{4}-\\d{2}-\\d{2}";
//        if (birthDate != null) {
//            String formattedDate = birthDate.format(dateTimeFormatter);
//            if (!formattedDate.matches(dateFormat)) {
//                throw new RuntimeException("Check the format of date: yyyy-mm-dd");
//            }
//        }

        validateSSN(patientDetails);

    }

    private static void validateSSN(PatientDetails patientDetails) {
        String ssnFormat = "\\d{3}-\\d{2}-\\d{4}";
        String ssn = patientDetails.getSsn();
        if (!ssn.matches(ssnFormat)){
            throw new RuntimeException("Check the SSN format and Try Again.");
        }
    }
}
