package com.patient.covid.dao;

import com.patient.covid.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentDao extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorID(Long doctorID);

    @Query("select ap from Appointment ap where ap.nurseID = ?1 and ap.confirmed = ?2")
    List<Appointment> findByNurseID(Long nurseID, String status);

    List<Appointment> findByPatientID(Long patientID);

    @Modifying
    @Query("delete from Appointment ap where ap.patientID = ?1")
    int deleteByPatientID(Long patientID);

}
