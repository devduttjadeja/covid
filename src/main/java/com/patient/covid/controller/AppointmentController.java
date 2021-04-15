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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

        List<Appointment> appointmentList = appointmentDao.findByNurseID(nurseID, "Confirmed with Nurse");
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

        return "doctor";
    }

    @GetMapping("/selfassessments_nurse/{patientID}")
    public String getSelfAssessmentResult(@PathVariable Long patientID, Model model) {
        model.addAttribute("patient", patientDao.findById(patientID).orElse(null));
        model.addAttribute("selfassessmentsOfPatient", patientSelfAssessmentDao.findByPatientID(patientID));
        return "self_assessment_nurse_results";
    }

    @GetMapping("/apppointment_ass_results_nurse/{patientID}")
    public String getSelfAssresultfornurse(@PathVariable Long patientID, Model model) {
        model.addAttribute("patient", patientDao.findById(patientID).orElse(null));
        model.addAttribute("selfassessmentsOfPatient", patientSelfAssessmentDao.findByPatientID(patientID));
        return "app_self_assessment_nurse_results";
    }

    @GetMapping("/view_appointment_patient/{patientID}")
    public String view_appointment_patient(@PathVariable Long patientID, Model model) {

        Patient patient = patientDao.findById(patientID).orElse(null);
        model.addAttribute("patient", patient);

        List<Appointment> appointments = appointmentDao.findByPatientID(patientID);
        model.addAttribute("appointments", appointments);

        return "view_appointment_patient";
    }

    @GetMapping("/confirm_patient/{patientID}/{nurseID}/{appDate}")
    public String confirm_appointment_nurse(@PathVariable Long patientID, @PathVariable Long nurseID,@PathVariable String appDate , Model model) throws ParseException {

        Patient patient = patientDao.findById(patientID).orElse(null);
        Nurse nurse = nurseDao.findById(nurseID).orElse(null);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date = simpleDateFormat.parse(appDate);

        Appointment appointment = new Appointment();
        appointment.setPatientID(patientID);
        appointment.setNurseID(nurseID);
        appointment.setPatientName(patient.getPatientName());
        appointment.setNurseName(nurse.getNurseName());
        appointment.setConfirmed("Confirmed with Nurse");

        appointment.setAppointmentDate(date);

        appointment.setNotes("The patient has appointment with nurse on " + date + ". Please wear a mask.");
        appointmentDao.save(appointment);

        // send confirmation email

        model.addAttribute("nurse", nurse);
        return "nurse";
    }

    @GetMapping("/confirm_patient_doctor/{patientID}/{doctorID}")
    public String confirm_appointment_doctor(@PathVariable Long patientID, @PathVariable Long doctorID, Model model) {

        Patient patient = patientDao.findById(patientID).orElse(null);
        Doctor doctor = doctorDao.findById(doctorID).orElse(null);


        List<Appointment> appList = appointmentDao.findByPatientDoctorID(patientID, doctorID);
        Collections.sort(appList, new Sortbyapp());
        Date date = appList.get(appList.size() - 1).getAppointmentDate();

        Appointment appointment = new Appointment();
        appointment.setPatientID(patientID);
        appointment.setPatientName(patient.getPatientName());
        appointment.setDoctorID(doctorID);
        appointment.setDoctorName(doctor.getDoctorName());
        appointment.setAppointmentDate(date);
        appointment.setConfirmed("Confirmed with Doctor");
        appointment.setNotes("The patient has appointment with doctor on " + date + ". Please wear a mask.");

        appointmentDao.save(appointment);
        model.addAttribute("doctor", doctor);
        return "doctor";
    }

    @GetMapping("/reject_patient_nurse/{patientID}/{nurseID}")
    public String reject_patient_nurse(@PathVariable Long patientID, @PathVariable Long nurseID, Model model) {

        Appointment appointment = new Appointment();
        Patient patient = patientDao.findById(patientID).orElse(null);
        Nurse nurse = nurseDao.findById(nurseID).orElse(null);

        appointment.setPatientID(patientID);
        appointment.setNurseID(nurseID);
        appointment.setPatientName(patient.getPatientName());
        appointment.setNurseName(nurse.getNurseName());
        appointment.setConfirmed("Not Required");
        appointment.setNotes("Symptoms are not sever. Please stay at home for next 14 days..");
        appointmentDao.save(appointment);

        // send rejecttion email

        model.addAttribute("nurse", nurse);
        return "nurse";
    }

    @GetMapping("/assign_patient_to_doctor/{patientID}/{nurseID}")
    public String assign_patient_to_doctor(@PathVariable Long patientID, @PathVariable Long nurseID, Model model) {

        Patient patient = patientDao.findById(patientID).orElse(null);
        Nurse nurse = nurseDao.findById(nurseID).orElse(null);
        List<Doctor> allDoctors = doctorDao.findAll();

        model.addAttribute("patient", patient);
        model.addAttribute("nurse", nurse);
        model.addAttribute("allDoctors", allDoctors);

        return "assign_patient_to_doctor";
    }

    @GetMapping("confirm_assignment_with_doctor")
    public String confirm_assignment_with_doctor(@RequestParam("appDate") String appDate, @RequestParam("PatientID") Long patientID, @RequestParam("DoctorID") Long doctorID) throws ParseException {

        Patient patient = patientDao.findById(patientID).orElse(null);
        Doctor doctor = doctorDao.findById(doctorID).orElse(null);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date = simpleDateFormat.parse(appDate);

        Appointment appointment = new Appointment();
        appointment.setPatientName(patient.getPatientName());
        appointment.setPatientID(patientID);
        appointment.setDoctorName(doctor.getDoctorName());
        appointment.setDoctorID(doctorID);
        appointment.setAppointmentDate(date);
        appointment.setNotes("Patient requires a check-up by a doctor");
        appointment.setConfirmed("Assigned to Doctor");
        appointmentDao.save(appointment);

        return "patient_list_nurse";
    }

    @GetMapping("/removePatientsAppointments")
    @ResponseBody
    public void removePatientsAppointments(@RequestParam List<Long> patientIDList) {
        for (long patient : patientIDList)
            appointmentDao.deleteByPatientID(patient);
    }

    @GetMapping("/removeNursesAppointments")
    @ResponseBody
    public void removeNursesAppointments(@RequestParam List<Long> nurseIDList) {
        for (long nurse : nurseIDList)
            appointmentDao.deleteByNurseID(nurse);
    }

    @GetMapping("/removeDoctorsAppointments")
    @ResponseBody
    public void removeDoctorsAppointments(@RequestParam List<Long> doctorIDList) {
        for (long doctor : doctorIDList)
            appointmentDao.deleteByDoctorID(doctor);
    }
}

class Sortbyapp implements Comparator<Appointment> {

    public int compare(Appointment a, Appointment b) {
        return (int) (a.getAppointmentId() - b.getAppointmentId());
    }
}
