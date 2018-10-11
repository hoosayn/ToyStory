package org.ngo.core.services;

import org.ngo.entity.Profile;
import org.ngo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class LoginService {
    private UserService userService;

    @SuppressWarnings("unused")
    public LoginService() {
        this(null);
    }

    @Autowired
    public LoginService(UserService userService){
        this.userService = userService;
    }

//    public Optional<Profile> login(User credentials){
//        return userService.loadUserByUsername(credentials.getUsername())
//                .filter()
//
//    }
}
