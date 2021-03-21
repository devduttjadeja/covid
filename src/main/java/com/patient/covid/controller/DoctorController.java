package com.patient.covid.controller;

import com.patient.covid.dao.DoctorDao;
import com.patient.covid.dao.UserDao;
import com.patient.covid.model.Doctor;
import com.patient.covid.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class DoctorController {

    @Autowired
    private DoctorDao doctorDao;

    @Autowired
    private UserDao userdao;

    @PostMapping(value = "/registerDoctor")
    public String addDoctor(@RequestParam("name") String name, @RequestParam("password") String password,
                            @RequestParam("confirmpassword") String confirmpassword, @RequestParam("dob") String dob,
                            @RequestParam("email") String email, @RequestParam("Registration_num") String registration_num,
                            @RequestParam("mobile") String mobile, @RequestParam("address") String address) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(dob);

        Doctor doctor = new Doctor();
        doctor.setDoctorName(name);
        doctor.setDateOfBirth(date);
        doctor.setEmail(email);
        doctor.setDoctorRegistrationNumber(registration_num);
        doctor.setPhone(mobile);
        doctor.setAddress(address);

        doctorDao.save(doctor);

        User user = new User();
        user.setUserName(email);
        user.setPassword(password);
        user.setRole("DOCTOR");
        userdao.save(user);

        return "reg_success";
    }
    
    @GetMapping("/removeDoctors")
    @ResponseBody
    public void removeDoctors(@RequestParam List<Long> doctorIDList) {
        for (long doctor : doctorIDList)
            doctorDao.deleteById(doctor);
    }
    
    @GetMapping("/doctors")
    @ResponseBody
    public List<Doctor> getAllDoctor() {
        return doctorDao.findAll();
    }
}
