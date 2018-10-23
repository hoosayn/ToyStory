package org.ngo.registration.core.services;

import org.springframework.beans.factory.annotation.Autowired;

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
