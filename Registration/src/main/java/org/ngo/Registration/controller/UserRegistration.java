package org.ngo.registration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserRegistration {

    @GetMapping(value = "/me")
    public String registrationFormSubmit(){
        return "success";
    }
}
