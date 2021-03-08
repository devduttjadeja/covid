package com.patient.covid.controller;

import com.patient.covid.dao.NurseDao;
import com.patient.covid.dao.PatientDao;
import com.patient.covid.dao.UserDao;
import com.patient.covid.model.Nurse;
import com.patient.covid.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class NurseController {

    @Autowired
    private NurseDao nurseDao;
    @Autowired
    private UserDao userDao;

    @PostMapping(value = "/registerNurse")
    public String addNurse(@RequestParam("name") String name, @RequestParam("dob") String dob,
                           @RequestParam("email") String email, @RequestParam("mobile") String mobile,
                           @RequestParam("address") String address,@RequestParam("Registration_num") String registration_num, @RequestParam("password") String password) throws ParseException {


        Nurse nurse = new Nurse();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(dob);

        nurse.setNurseName(name);
        nurse.setAddress(address);
        nurse.setDateOfBirth(date);
        nurse.setNurseRegistrationNumber(registration_num);
        nurse.setPhone(mobile);
        nurse.setEmail(email);
        nurseDao.save(nurse);

        User user = new User();

        user.setUserName(email);
        user.setPassword(password);
        user.setRole("NURSE");
        userDao.save(user);
        return "redirect:/login.html";
    }

    @DeleteMapping("/deleteNurse/{nurseID}")
    public void deleteNurse(@PathVariable Long nurseID) {
        nurseDao.deleteById(nurseID);
    }
}