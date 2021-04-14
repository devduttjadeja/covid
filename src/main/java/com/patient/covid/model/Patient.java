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
public class Patient {

    @Getter
    @Id
    @GeneratedValue
    Long patientID;

    @Getter
    @Setter
    String patientName;

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

    @Getter
    @Setter
    String registrationDate;

}
