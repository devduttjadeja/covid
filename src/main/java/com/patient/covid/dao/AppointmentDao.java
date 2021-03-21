package com.patient.covid.dao;

import com.patient.covid.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentDao extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorID(Long doctorID);
}
