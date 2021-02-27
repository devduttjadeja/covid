package com.patient.covid.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class User {

    @Getter
    @Id
    @GeneratedValue
    Long userID;

    @Setter
    @Getter
    String userName;

    @Setter
    @Getter
    String password;

    @Getter
    @Setter
    String role;

}
