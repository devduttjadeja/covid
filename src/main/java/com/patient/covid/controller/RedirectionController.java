package com.patient.covid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectionController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/user_reg")
    public String user_reg() {
        return "user_reg";
    }

    @GetMapping("/doctor_reg")
    public String doctor_reg() {
        return "doctor_reg";
    }

    @GetMapping("/nurse_reg")
    public String nurse_reg() {
        return "nurse_reg";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/news")
    public String news() {
        return "news";
    }

    @GetMapping("/aboutus")
    public String aboutus() {
        return "aboutus";
    }

    @GetMapping("/self-assessment")
    public String self_assessment() {
        return "self-assessment";
    }

    @GetMapping("/appointments")
    public String appointments() {
        return "appointments";
    }

    @GetMapping("/patient_list")
    public String patient_list() {
        return "patient_list";
    }

    @GetMapping("/self_assessment_result")
    public String self_assessment_result() {
        return "self_assessment_result";
    }

}
