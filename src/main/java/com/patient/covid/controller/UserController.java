package com.patient.covid.controller;

import com.patient.covid.dao.PatientDao;
import com.patient.covid.dao.UserDao;
import com.patient.covid.model.Patient;
import com.patient.covid.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PatientDao patientDao;

    @PostMapping("/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("birthday") String birthday) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(birthday);

        Patient patient = new Patient();
        patient.setPatientName("Devdutt");
        patient.setAddress("Montreal");
        patient.setEmail("devduttjadeja01@gmail.com");
        patient.setPhone("+1 438-357-9977");
        patient.setDateOfBirth(date);

        User user = userDao.findByUserNameAndPassword(userName, password);
        patientDao.save(patient);

        if (user != null) {
            return "redirect:/home.html";
        }

        return "redirect:/invalidLogin.html";

    }


}
