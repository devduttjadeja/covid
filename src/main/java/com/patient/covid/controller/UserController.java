package com.patient.covid.controller;

import com.patient.covid.dao.PatientDao;
import com.patient.covid.dao.UserDao;
import com.patient.covid.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PatientDao patientDao;

    @PostMapping("/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password) {

        User user = userDao.findByUserNameAndPassword(userName, password);

        if (user != null) {
            return "redirect:/home.html";
        }

        return "redirect:/invalidLogin.html";

    }


}
