package com.patient.covid.controller;

import com.patient.covid.dao.PatientDao;
import com.patient.covid.dao.PatientSelfAssessmentDao;
import com.patient.covid.dao.UserDao;
import com.patient.covid.model.Patient;
import com.patient.covid.model.PatientSelfAssesment;
import com.patient.covid.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class PatientController {

    @Autowired
    private PatientDao patientDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PatientSelfAssessmentDao patientSelfAssessmentDao;

    @GetMapping("/patients")
    @ResponseBody
    public List<Patient> getAllPatient() {
        return patientDao.findAll();
    }

    @GetMapping("/removePatients")
    @ResponseBody
    public void removePatients(@RequestParam List<Long> patientIDList) {
        for (long patient : patientIDList)
            patientDao.deleteById(patient);
    }

    @GetMapping("/selfassessments")
    @ResponseBody
    public List<PatientSelfAssesment> getSelfAssessmentResult() {
        return patientSelfAssessmentDao.findAll();
    }

    @GetMapping("/selfassessments/{patientID}")
    public String getSelfAssessmentResult(@PathVariable Long patientID, Model model) {
        model.addAttribute("patient", patientDao.findById(patientID).orElse(null));
        model.addAttribute("selfassessmentsOfPatient", patientSelfAssessmentDao.findByPatientID(patientID));
        return "self_assessment_result";
    }

    @GetMapping("/patients/{patientID}")
    public Patient getPatient(@PathVariable Long patientID) {
        return patientDao.findById(patientID).orElse(null);
    }

    @PostMapping(value = "/registerPatient")
    public String registerPatient(@RequestParam("name") String name, @RequestParam("dob") String dob,
                                  @RequestParam("email") String email, @RequestParam("phone") String phone,
                                  @RequestParam("address") String address, @RequestParam("password") String password) throws ParseException {
        Patient patient = new Patient();
        patient.setPatientName(name);
        patient.setEmail(email);
        patient.setAddress(address);
        patient.setPhone(phone);
        patient.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(dob));
        patientDao.save(patient);

        User user = new User();
        user.setUserName(email);
        user.setPassword(password);
        user.setRole("PATIENT");
        userDao.save(user);

        return "reg_success";
    }

    @PostMapping(value = "/self_assessmentform")
    public String self_assessmentform(@RequestParam("q1") String q1, @RequestParam("q2") String q2,
                                      @RequestParam("q3") String q3, @RequestParam("q4") String q4,
                                      @RequestParam("q5") String q5, @RequestParam("q6") String q6,
                                      @RequestParam("q7") String q7, @RequestParam("q8") String q8, @RequestParam("patientID") String patientID,
                                      Model model) {
        PatientSelfAssesment patientSelfAssesment = new PatientSelfAssesment();
        patientSelfAssesment.setDifficultyInBreathing(q1);
        patientSelfAssesment.setAge(q2);
        patientSelfAssesment.setSymptoms1(q3);
        patientSelfAssesment.setSymptoms2(q4);
        patientSelfAssesment.setSituation(q5);
        patientSelfAssesment.setCloseContact(q6);
        patientSelfAssesment.setTested(q7);
        patientSelfAssesment.setTravel(q8);
        patientSelfAssesment.setPatientID(Long.parseLong(patientID));
        patientSelfAssesment.setDate(new Date());
        patientSelfAssesment.setAssessmentDate(new Date().toString().substring(0, 10).trim() + " 2021");    
        patientSelfAssessmentDao.save(patientSelfAssesment);

        model.addAttribute("patient", patientDao.findById(Long.parseLong(patientID)).orElse(null));

        return "user";
    }

    @GetMapping("/user/{patientID}")
    public String getPatient(@PathVariable Long patientID, Model model) {
        Patient patient = patientDao.findById(patientID).orElse(null);
        model.addAttribute("patient", patient);
        return "user";
    }

    @DeleteMapping("/deletePatient/{patientID}")
    public void deletePatient(@PathVariable Long patientID) {
        patientDao.deleteById(patientID);
    }
}
