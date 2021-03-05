package com.patient.covid.dao;

import com.patient.covid.model.Doctor;
import com.patient.covid.model.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorDao extends JpaRepository<Doctor, Long> {
}
