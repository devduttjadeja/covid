package com.patient.covid.controller;

import com.patient.covid.dao.DoctorDao;
import com.patient.covid.dao.NurseDao;
import com.patient.covid.dao.PatientDao;
import com.patient.covid.dao.UserDao;
import com.patient.covid.model.Doctor;
import com.patient.covid.model.Nurse;
import com.patient.covid.model.Patient;
import com.patient.covid.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PatientDao patientDao;
    @Autowired
    private NurseDao nurseDao;
    @Autowired
    private DoctorDao doctorDao;

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password, ModelMap model) {

        User user = userDao.findByUserNameAndPassword(email, password);

        if (user != null) {
            String role = user.getRole();

            if (role.equals("PATIENT")) {
                Patient patient = patientDao.findByEmail(email);
                model.addAttribute("patient", patient);
                return "user";
            }
            if (role.equals("DOCTOR")) {
                Doctor doctor = doctorDao.findByEmail(email);
                model.addAttribute("doctor", doctor);
                return "doctor";
            }
            if (role.equals("NURSE")) {
                Nurse nurse = nurseDao.findByEmail(email);
                model.addAttribute("nurse", nurse);
                return "nurse";
            }
        }

        return "invalidlogin";
    }


}
