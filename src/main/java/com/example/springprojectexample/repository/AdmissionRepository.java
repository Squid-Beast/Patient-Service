package com.example.springprojectexample.repository;

import com.example.springprojectexample.entity.Admission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    @Query("SELECT a FROM Admission a WHERE a.patient.id = ?1")
    public List<Admission> getAdmissionsByPatientId(Long patientId);

    @Query("SELECT a FROM Admission a WHERE a.doctor.id = ?1")
    public List<Admission> getAdmissionDetailsByDoctorId(Long doctorId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Admission a WHERE a.patient.id = ?1")
    public void deleteAdmissionByPatientId(Long patientId);
}