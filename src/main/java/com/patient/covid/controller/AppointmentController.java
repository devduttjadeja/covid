package com.patient.covid.controller;

import com.patient.covid.dao.AppointmentDao;
import com.patient.covid.dao.DoctorDao;
import com.patient.covid.dao.NurseDao;
import com.patient.covid.model.Appointment;
import com.patient.covid.model.Doctor;
import com.patient.covid.model.Nurse;
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

    @Autowired
    private NurseDao nurseDao;

    @GetMapping("/viewAppointments/doctor/{doctorID}")
    public  String doctor_handle(@PathVariable Long doctorID, Model model){


        Doctor doctor = doctorDao.findById(doctorID).orElse(null);

        List<Appointment> appointmentList= appointmentDao.findByDoctorID(doctorID);
        model.addAttribute("appointmentList", appointmentList);

        model.addAttribute("name", doctor.getDoctorName());
        model.addAttribute("doctor",doctor);


        return "appointment_list";
    }


    @GetMapping("/viewAppointments/nurse/{nurseID}")
    public  String nurse_handle(@PathVariable Long nurseID, Model model){


        Nurse nurse = nurseDao.findById(nurseID).orElse(null);

        List<Appointment> appointmentList= appointmentDao.findByNurseID(nurseID);
        model.addAttribute("appointmentList", appointmentList);

        model.addAttribute("name", nurse.getNurseName());
        model.addAttribute("nurse", nurse);

        return "appointment_list";
    }

}
