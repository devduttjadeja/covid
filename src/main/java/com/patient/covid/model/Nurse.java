package com.patient.covid.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Nurse {

    @Getter
    @Id
    @GeneratedValue
    Long nurseID;

    @Getter
    @Setter
    String nurseName;

    @Getter
    @Setter
    String address;

}
