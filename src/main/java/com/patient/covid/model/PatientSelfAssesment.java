package com.patient.covid.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class PatientSelfAssesment {

    @Getter
    @Id
    @GeneratedValue
    Long selfAssessmentID;

    @Setter
    @Getter
    Long patientID;

    @Getter
    @Setter
    String difficultyInBreathing;

    @Getter
    @Setter
    String age;

    @Getter
    @Setter
    String symptoms1;

    @Getter
    @Setter
    String symptoms2;

    @Getter
    @Setter
    String situation;

    @Setter
    @Getter
    String closeContact;

    @Setter
    @Getter
    String tested;

    @Setter
    @Getter
    String travel;

}
