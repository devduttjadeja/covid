package com.patient.covid.controller;

import com.patient.covid.dao.*;
import com.patient.covid.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    @Autowired
    private PatientSelfAssessmentDao assessmentDao;

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
            if (role.equals("ADMIN")) {
                return "admin";
            }

        }

        return "invalidlogin";
    }

    @GetMapping("/generate_report")
    public String generateReport(@RequestParam("rDate") String rDate, @RequestParam("rWeek") String rWeek,
                                 @RequestParam("rMonth") String rMonth, Model model){
        List<Patient> patientList = patientDao.findAll();
        List<Patient> filteredList = new ArrayList<Patient>();
        if (rWeek == "" && rMonth==""){
            // Date
            for(int i=0;i< patientList.size();i++){
                if(patientList.get(i).getRegistrationDate().equals(rDate))
                    filteredList.add(patientList.get(i));
            }
            model.addAttribute("content","Number of patients registered on "+rDate);

        }else if (rDate=="" && rMonth==""){
            // Week
            for(int i=0;i<patientList.size();i++ ){
                String regDate = patientList.get(i).getRegistrationDate();
                String[] ymd = regDate.split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(ymd[0]), Integer.parseInt(ymd[1]), Integer.parseInt(ymd[2]));
                int weekOfYear = date.get(WeekFields.of(Locale.getDefault()).weekOfYear())-1;
                if (rWeek.endsWith(Integer.toString(weekOfYear))){
                    filteredList.add(patientList.get(i));
                }
            }
            model.addAttribute("content","Number of patients registered in Week "+rWeek);

        }else if (rDate=="" && rWeek==""){
            // Month
            for(int i=0;i<patientList.size();i++ ){
                String regDate = patientList.get(i).getRegistrationDate();
                String[] ymd = regDate.split("-");
                if (rMonth.endsWith(ymd[1])){
                    filteredList.add(patientList.get(i));
                }
            }
            model.addAttribute("content","Number of patients registered in the month "+rMonth);
        }else{
            model.addAttribute("content","Please choose one field only");
        }


        model.addAttribute("numPatients",filteredList.size());
        if(filteredList.size()>0)
            model.addAttribute("hasContent");
        return "get_report";
    }






}
