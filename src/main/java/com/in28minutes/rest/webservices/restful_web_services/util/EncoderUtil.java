package com.in28minutes.rest.webservices.restful_web_services.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class EncoderUtil {
    private final static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public static BCryptPasswordEncoder getEncoder() {
        return encoder;
    }
}
