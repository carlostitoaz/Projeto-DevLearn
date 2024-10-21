package com.DevLearn.model;

import java.util.Date;

public class User {
    private int id_user;
    private String name;
    private String email;
    private String password;
    private int id_userType;
    private Date registrationDate;
    private boolean inactive;

    public User() {}

    public User(String name, String email, String password, int id_userType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id_userType = id_userType;
    }

    // Getters and Setters

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId_userType() {
        return id_userType;
    }

    public void setId_userType(int id_userType) {
        this.id_userType = id_userType;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }
}
