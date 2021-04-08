package com.patient.covid.dao;

import com.patient.covid.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AppointmentDao extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorID(Long doctorID);

    @Query("select ap from Appointment ap where ap.nurseID = ?1 and ap.confirmed = ?2")
    List<Appointment> findByNurseID(Long nurseID, String status);

    List<Appointment> findByPatientID(Long patientID);

    @Query("select ap from Appointment ap where ap.patientID = ?1 and ap.doctorID = ?2")
    List<Appointment> findByPatientDoctorID(Long patientID, Long doctorID);


    @Transactional
    @Modifying
    @Query("delete from Appointment ap where ap.patientID = ?1")
    int deleteByPatientID(Long patientID);

    @Transactional
    @Modifying
    @Query("delete from Appointment ap where ap.nurseID = ?1")
    int deleteByNurseID(Long nurseID);

    @Transactional
    @Modifying
    @Query("delete from Appointment ap where ap.doctorID = ?1")
    int deleteByDoctorID(Long doctorID);

}
