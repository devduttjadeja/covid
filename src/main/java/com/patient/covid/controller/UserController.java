package com.patient.covid.controller;

import com.patient.covid.dao.PatientDao;
import com.patient.covid.dao.UserDao;
import com.patient.covid.model.Patient;
import com.patient.covid.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PatientDao patientDao;

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password, Model model) {

        User user = userDao.findByUserNameAndPassword(email, password);

        if (user != null) {
            String role = user.getRole();

            if (role.equals("PATIENT")) {
                Patient patient = patientDao.findByEmail(email);
                model.addAttribute("patient", patient);
                return "user";
            }
            if (role.equals("DOCTOR")) {


            }
            if (role.equals("NURSE")) {


            }
        }

        return "invalidPage";
    }


}
