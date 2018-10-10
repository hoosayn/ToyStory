package org.ngo.controller;

import org.ngo.core.services.ProfileService;
import org.ngo.core.services.UserService;
import org.ngo.entity.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    @SuppressWarnings("unused")
    public ProfileController(){
        this(null);
    }

    @RequestMapping(path="/details/{username}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Profile details(@PathVariable String username){
        return userService.loadUserByUsername(username)
                .orElseThrow(() -> new ProfileNotFoundException(username));
    }
}
