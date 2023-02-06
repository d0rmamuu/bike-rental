package com.ridam.GrabACycle.repository;

import com.ridam.GrabACycle.enums.UserLogged;
import com.ridam.GrabACycle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    public List<User> findByEmail(String email);
}
