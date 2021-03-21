package com.patient.covid.controller;

import com.patient.covid.dao.AppointmentDao;
import com.patient.covid.dao.DoctorDao;
import com.patient.covid.model.Appointment;
import com.patient.covid.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private DoctorDao doctorDao;

    @GetMapping("/viewAppointments/{doctorID}")
    public  String handle(@PathVariable Long doctorID, Model model){

        Appointment appointment = new Appointment();
        appointment.setDoctorID(doctorID);
        appointment.setNotes("Hiii ");
        appointment.setPatientID(1L);
        appointment.setPatientName("dex");

        appointmentDao.save(appointment);
        Doctor doctor = doctorDao.findById(doctorID).orElse(null);

        List<Appointment> appointmentList= appointmentDao.findByDoctorID(doctorID);
        model.addAttribute("appointmentList", appointmentList);

        model.addAttribute("doctor", doctor);


        return "appointment_list";
    }
}
