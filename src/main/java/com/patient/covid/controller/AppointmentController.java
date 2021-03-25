package com.patient.covid.controller;

import com.patient.covid.dao.*;
import com.patient.covid.model.Appointment;
import com.patient.covid.model.Doctor;
import com.patient.covid.model.Nurse;
import com.patient.covid.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private PatientDao patientDao;
    @Autowired
    private PatientSelfAssessmentDao patientSelfAssessmentDao;

    @GetMapping("/viewAppointments/doctor/{doctorID}")
    public String doctor_handle(@PathVariable Long doctorID, Model model) {


        Doctor doctor = doctorDao.findById(doctorID).orElse(null);

        List<Appointment> appointmentList = appointmentDao.findByDoctorID(doctorID);

        model.addAttribute("appointmentList", appointmentList);
        model.addAttribute("name", doctor.getDoctorName());
        model.addAttribute("doctor", doctor);

        return "appointment_list_doctor";
    }


    @GetMapping("/viewAppointments/nurse/{nurseID}")
    public String nurse_handle(@PathVariable Long nurseID, Model model) {


        Nurse nurse = nurseDao.findById(nurseID).orElse(null);

        List<Appointment> appointmentList = appointmentDao.findByNurseID(nurseID);
        model.addAttribute("appointmentList", appointmentList);

        model.addAttribute("name", nurse.getNurseName());
        model.addAttribute("nurse", nurse);


        return "appointment_list_nurse";
    }

    @GetMapping("/self_assesment_doctor/{patientID}")
    public String show_self_assements_resutls_doctor(@PathVariable Long patientID, Model model) {

        Patient patient = patientDao.findById(patientID).orElse(null);
        model.addAttribute("patient", patient);
        model.addAttribute("selfassessmentsOfPatient", patientSelfAssessmentDao.findByPatientID(patientID));
        model.addAttribute("name", patient.getPatientName());

        return "self_assesment_doctor_results";
    }

    @GetMapping("/self_assesment_nurse/{patientID}")
    public String show_self_assements_resutls_nurse(@PathVariable Long patientID, Model model) {

        model.addAttribute("patient", patientDao.findById(patientID).orElse(null));
        model.addAttribute("selfassessmentsOfPatient", patientSelfAssessmentDao.findByPatientID(patientID));

        return "self_assessment_result";
    }

    @Transactional
    @GetMapping("/reject_patient/{patientID}/{doctorID}")
    public String doctor_reject_patient(@PathVariable Long patientID, @PathVariable Long doctorID, Model model) {

        appointmentDao.deleteByPatientID(patientID);
        model.addAttribute("patient", patientDao.findById(patientID).orElse(null));
        model.addAttribute("selfassessmentsOfPatient", patientSelfAssessmentDao.findByPatientID(patientID));

        Doctor doctor = doctorDao.findById(doctorID).orElse(null);

        List<Appointment> appointmentList = appointmentDao.findByDoctorID(doctorID);

        model.addAttribute("appointmentList", appointmentList);
        model.addAttribute("name", doctor.getDoctorName());
        model.addAttribute("doctor", doctor);

        return "appointment_list_doctor";
    }

    @GetMapping("/selfassessments_nurse/{patientID}")
    public String getSelfAssessmentResult(@PathVariable Long patientID, Model model) {
        model.addAttribute("patient", patientDao.findById(patientID).orElse(null));
        model.addAttribute("selfassessmentsOfPatient", patientSelfAssessmentDao.findByPatientID(patientID));
        return "self_assessment_nurse_results";
    }

    @GetMapping("/view_appointment_patient/{patientID}")
    public String view_appointment_patient(@PathVariable Long patientID, Model model) {

        Patient patient = patientDao.findById(patientID).orElse(null);
        model.addAttribute("patient", patient);

        List<Appointment> appointments = appointmentDao.findByPatientID(patientID);
        model.addAttribute("appointments", appointments);

        return "view_appointment_patient";
    }

}
