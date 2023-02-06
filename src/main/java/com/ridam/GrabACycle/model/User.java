package com.ridam.GrabACycle.model;

import com.ridam.GrabACycle.enums.UserLogged;
import com.ridam.GrabACycle.enums.UserRole;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String userName;
    private String password;
    private String email;
    @Enumerated(EnumType.ORDINAL)
    private UserRole userRole;
    @OneToMany(fetch = FetchType.EAGER)
    private Collection<Cycle>cycles;
    private UserLogged logStatus;

    public UserLogged getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(UserLogged logStatus) {
        this.logStatus = logStatus;
    }

    public Collection<Cycle> getCycles() {
        return cycles;
    }

    public void setCycles(Collection<Cycle> cycles) {
        this.cycles = cycles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
