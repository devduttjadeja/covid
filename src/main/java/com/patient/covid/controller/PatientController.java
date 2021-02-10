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
    public List<Patient> getAllPatient() {
        return patientDao.findAll();
    }

    @GetMapping("/patients/{patientID}")
    public Patient getPatient(@PathVariable Long patientID) {
        return patientDao.findById(patientID).orElse(null);
    }

    @PostMapping(value = "/registerPatient")
    public void addPatient(@RequestBody Patient patient) {
        patientDao.save(patient);
    }

    @DeleteMapping("/deletePatient/{patientID}")
    public void deletePatient(@PathVariable Long patientID) {
        patientDao.deleteById(patientID);
    }
}
