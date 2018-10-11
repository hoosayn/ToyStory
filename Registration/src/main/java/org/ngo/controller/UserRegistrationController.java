package org.ngo.controller;

import org.ngo.core.services.UserService;
import org.ngo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
//@RequestMapping("/api/registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public String registrationFormSubmit(){
        return "success";
    }

    @GetMapping("/test")
    public String test(){
        return "done!";
    }

    @PostMapping("/registration")
    public HttpServletResponse save(@RequestBody User user, HttpServletResponse response){
        User respUser = userService.save(user);
        response.setHeader("Token","testToken");
        return response;
    }
}
