package com.patient.covid.dao;

import com.patient.covid.model.PatientSelfAssesment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientSelfAssessmentDao extends JpaRepository<PatientSelfAssesment, Long> {
    List<PatientSelfAssesment> findByPatientID(Long patientID);

}

