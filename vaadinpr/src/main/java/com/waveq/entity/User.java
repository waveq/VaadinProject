package com.waveq.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Szymon on 2015-03-15.
 */
public class User {

    String username;
    String email;
    String password;
    Date yob;

    public User() {
    }

    public User(String username, String email, String password, Date yob) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.yob = yob;
    }

    public Date getYob() {
        return yob;
    }

    public void setYob(Date yob) {
        this.yob = yob;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
