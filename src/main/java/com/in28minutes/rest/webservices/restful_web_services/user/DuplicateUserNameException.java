package com.in28minutes.rest.webservices.restful_web_services.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST)
public class DuplicateUserNameException extends RuntimeException {

    public DuplicateUserNameException(String message) {
        super(message);
    }
}
