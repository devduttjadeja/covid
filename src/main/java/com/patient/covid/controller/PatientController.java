package com.patient.covid.controller;

import com.patient.covid.dao.PatientDao;
import com.patient.covid.dao.UserDao;
import com.patient.covid.model.Patient;
import com.patient.covid.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class PatientController {

    @Autowired
    private PatientDao patientDao;
    @Autowired
    private UserDao userDao;

    @GetMapping("/patients")
    @ResponseBody
    public List<Patient> getAllPatient() {
        return patientDao.findAll();
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

        return "redirect:/index.html"; // redirect to home page of patient
    }

    @DeleteMapping("/deletePatient/{patientID}")
    public void deletePatient(@PathVariable Long patientID) {
        patientDao.deleteById(patientID);
    }
}
