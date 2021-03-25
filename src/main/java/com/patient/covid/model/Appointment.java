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
public class Appointment {


    @Getter
    @Id
    @GeneratedValue
    Long appointmentId;

    @Getter
    @Setter
    String patientName;

    @Setter
    @Getter
    Long patientID;

    @Getter
    @Setter
    Long doctorID;

    @Getter
    @Setter
    String doctorName;

    @Getter
    @Setter
    Long nurseID;

    @Getter
    @Setter
    String nurseName;

    @Getter
    @Setter
    Date appointmentDate;

    @Getter
    @Setter
    String notes;

    @Getter
    @Setter
    String confirmed;

}
