package org.ngo.controller;

import org.ngo.core.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/login")
public class LoginController {

//    private final LoginService loginService;
//    private final JwtService jwtService;
//
//    @SuppressWarnings("unused")
//    public LoginController() {
//        this(null, null);
//    }

//    @Autowired
//    public LoginController(LoginService loginService, JwtService jwtService) {
//        this.loginService = loginService;
//        this.jwtService = jwtService;
//    }

    @GetMapping("/home")
    public String loginSuccessPage(){
        return "login successfull";
    }
//    @RequestMapping(path = "",
//            method = RequestMethod.POST,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public MinimalProfile login(@RequestBody LoginCredentials credentials,
//                                HttpServletResponse response) {
//        return loginService.login(credentials)
//                .map(minimalProfile -> {
//                    try {
//                        response.setHeader("Token", jwtService.tokenFor(minimalProfile));
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                    return minimalProfile;
//                })
//                .orElseThrow(() -> new FailedToLoginException(credentials.getUsername()));
//    }
}
