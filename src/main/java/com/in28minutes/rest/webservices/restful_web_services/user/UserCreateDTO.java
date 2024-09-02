package com.in28minutes.rest.webservices.restful_web_services.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public class UserCreateDTO {
    @Size(min=2, message = "UserName should have at least 2 characters")
    private String userName;
    @Past(message = "Birth Date should be in the past")
    private LocalDate birthDate;
    @Size(min=6, max = 12, message = "Password should have between 6 to 12 characters")
    private String password;

    public UserCreateDTO(String userName, LocalDate birthDate, String password) {
        this.userName = userName;
        this.birthDate = birthDate;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
