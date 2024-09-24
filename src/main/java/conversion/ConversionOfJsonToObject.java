package conversion;

import com.example.springprojectexample.pojo.PatientDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ConversionOfJsonToObject {
    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayList<PatientDetails> patientDetailsArrayList = objectMapper.readValue(new File("output.json"),ArrayList.class);
            System.out.println(patientDetailsArrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
