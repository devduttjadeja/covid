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
    String symptoms;
    // Fever, Sudden loss of sense of smell, Recent cough Sore throat, Runny nose or congestion

}
