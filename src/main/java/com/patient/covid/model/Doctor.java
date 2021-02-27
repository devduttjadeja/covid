package com.patient.covid.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table
public class Doctor {

    @Getter
    @Id
    @GeneratedValue
    Long doctorID;

    @Getter
    @Setter
    String doctorName;

    @Getter
    @Setter
    Date dateOfBirth;

    @Getter
    @Setter
    String address;

    @Setter
    @Getter
    String phone;

    @Setter
    @Getter
    String email;

    @Setter
    @Getter
    String doctorRegistrationNumber;

}
