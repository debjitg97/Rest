package com.in28minutes.rest.webservices.restful_web_services.user;

import jakarta.validation.constraints.Size;

public class PostDTO {
    @Size(min=10)
    private String description;

    protected PostDTO() {}

    public PostDTO(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
