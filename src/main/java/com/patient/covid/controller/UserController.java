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

import java.util.ArrayList;
import java.util.List;

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
    public String generateReport(@RequestParam("ryear") String year, @RequestParam("rmonth") String month, Model model){
        List<PatientSelfAssesment> assesmentList = assessmentDao.findAll();
        String m="";
        if(month.endsWith("03"))
            m="Mar";
        else if(month.endsWith("04"))
            m="Apr";
        else
            m="none";
        List<PatientSelfAssesment> filteredList = assessmentFilter(assesmentList,year,m);

        model.addAttribute("assessmentList",filteredList);
        if(filteredList.size()>0)
            model.addAttribute("hasContent");
        return "get_report";
    }

    private List<PatientSelfAssesment> assessmentFilter(List<PatientSelfAssesment> assesmentList, String year, String month) {
        List<PatientSelfAssesment> filteredList = new ArrayList<PatientSelfAssesment>();
        for(PatientSelfAssesment a : assesmentList){
            if(a.getAssessmentDate().endsWith(year)){
                if (month !=""){
                    if(a.getAssessmentDate().contains(month)){
                        filteredList.add(a);
                    }
                }else {
                    filteredList.add(a);
                }
            }
        }
        return  filteredList;
    }


}
