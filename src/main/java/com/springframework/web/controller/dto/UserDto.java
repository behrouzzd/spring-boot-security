package com.springframework.web.controller.dto;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Behrouz-ZD on 16/09/2017.
 */
public class UserDto {

    @NotNull
    @Email
    private String username;


    @NotNull
    @Size(min = 3, max = 10)
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
