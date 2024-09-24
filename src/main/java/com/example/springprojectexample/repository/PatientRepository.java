package com.example.springprojectexample.repository;

import com.example.springprojectexample.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {
    @Query("SELECT p FROM Patient p WHERE p.id=?1")
        //Annotation creates custom queries
    Patient findPatientById(Integer id);

}