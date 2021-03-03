package com.patient.covid.controller;

import com.patient.covid.dao.PatientDao;
import com.patient.covid.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PatientController {

    @Autowired
    private PatientDao patientDao;

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
    public String addPatient(@RequestParam("name") String name, @RequestParam("dob") String dob,
                           @RequestParam("email") String email, @RequestParam("phone") String phone,
                           @RequestParam("address") String address, @RequestParam("password") String password) {
        Patient patient = new Patient();
        patient.setPatientName(name);
        patient.setEmail(email);
        patientDao.save(patient);

        return "redirect:/index.html";
    }

    @DeleteMapping("/deletePatient/{patientID}")
    public void deletePatient(@PathVariable Long patientID) {
        patientDao.deleteById(patientID);
    }
}
