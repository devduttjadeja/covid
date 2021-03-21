package com.patient.covid.dao;

import com.patient.covid.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByUserNameAndPassword(String userName, String password);
}
