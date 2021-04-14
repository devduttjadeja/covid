package com.patient.covid.dao;

import com.patient.covid.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByUserNameAndPassword(String userName, String password);

    @Transactional
    @Modifying
    @Query("delete from User u where u.userName = ?1")
    void deleteByEmail(String email);
}
