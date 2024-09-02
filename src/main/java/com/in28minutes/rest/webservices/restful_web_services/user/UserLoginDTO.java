package com.in28minutes.rest.webservices.restful_web_services.user;

import jakarta.validation.constraints.NotNull;

public class UserLoginDTO {
    @NotNull(message = "userName is required for login")
    private String userName;
    @NotNull(message = "password is required for login")
    private String password;

    public UserLoginDTO(String userName, String password) {
        this.userName = userName;
        this.password = password;
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
}
