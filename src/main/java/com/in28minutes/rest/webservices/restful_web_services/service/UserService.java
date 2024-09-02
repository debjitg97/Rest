package com.in28minutes.rest.webservices.restful_web_services.service;

import com.in28minutes.rest.webservices.restful_web_services.jpa.UserRepository;
import com.in28minutes.rest.webservices.restful_web_services.user.User;
import com.in28minutes.rest.webservices.restful_web_services.user.UserLogin;
import com.in28minutes.rest.webservices.restful_web_services.user.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = repository.findById(userName);
        if (user.isEmpty()) {
            throw new UserNotFoundException("userName " + userName + " does not exists!");
        }
        return new UserLogin(user.get().getUserName(), user.get().getPassword());
    }
}
