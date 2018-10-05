package org.ngo.registration.controller;

import org.ngo.registration.core.UserService;
import org.ngo.registration.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/me")
    public String registrationFormSubmit(){
        return "success";
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody User user ){
        User respUser = userService.save(user);
        return ResponseEntity.ok().body(respUser);
    }
}
