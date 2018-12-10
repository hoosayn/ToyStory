package org.ngo.registration.controller;

import org.ngo.registration.common.constant.RegistrationConstant;
import org.ngo.registration.common.util.TheToyStoryUtils;
import org.ngo.registration.communication.interservice.MessageSenderService;
import org.ngo.registration.core.repository.RoleRepository;
import org.ngo.registration.core.repository.UserRepository;
import org.ngo.registration.core.services.JwtService;
import org.ngo.registration.core.services.UserService;
import org.ngo.registration.core.services.exceptions.TheToyStoryException;
import org.ngo.registration.entity.Role;
import org.ngo.registration.entity.User;
import org.ngo.registration.expections.NgoExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;

@RestController
@RequestMapping("/registration/api/v1.0")
public class UserRegistrationController {

    Logger LOGGER = LoggerFactory.getLogger(UserRegistrationController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MessageSenderService messageSenderService;
    @Autowired
    private UserRepository userRepository;


    @Value("${lucky-word}") String luckyWord;

    @GetMapping(value = "/")
    public String registrationFormSubmit(HttpServletRequest req, HttpServletResponse res){
        if (req.getHeader("Test") != null) {
            res.addHeader("Test", req.getHeader("Test"));
        }
        return "hello kitty";
    }

    @GetMapping("/test")
    public String test(HttpServletResponse response){
        User user = new User();
        user.setUsername("asif");
        response.setHeader("Token", jwtService.generateToken(user));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        boolean authorized = authorities.contains(new SimpleGrantedAuthority("ADMIN"));
        System.out.println("===============");
        System.out.println(auth.getName());
        authorities.forEach(System.out::println);
        return "done!";
    }

    @PostMapping("/registration")
    public String save(@RequestBody User user, HttpServletResponse response){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Collection<Role> roles = roleRepository.findByRolecode(
                Arrays.asList(user.getRoleType().split(",")))
                .orElseThrow(() -> new NgoExceptions("Role Not Found"));

        user.setRoles(roles);
        User respUser = userService.save(user);
        respUser.getRoles().stream().forEach(r -> roleRepository.addRolesToUser(respUser.getId(),
                r.getId()));
        user.setToken(jwtService.generateToken(user));
        messageSenderService.send(user.getToken());
//        response.setHeader("Set-Cookie", "access_token="+user.getToken());
        return "postcallreturnstoken";

//        {"id":"1","username":"asif1","password":"a","roleType":"101","title":"Mr.","firstName":"asif","lastName":"shaikh"}  json request to register
//        https://stormpath.com/blog/where-to-store-your-jwts-cookies-vs-html5-web-storage
//        https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
    }

   /* @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletRequest req, HttpServletResponse res){
        User user = this.userRepository.findByUsername(null)
                .orElseThrow(
                        ()->new TheToyStoryException(TheToyStoryUtils.Constants.Errors.WRONG_USERNAME_OR_PASSWORD));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Set-Cookie", "access_token="+jwtService.generateToken(user));
        return new ResponseEntity<String>("User Authenticated..", httpHeaders, HttpStatus.OK);
    }*/

    @GetMapping("/members/common")
    public String securedCommon(HttpServletRequest request, HttpServletResponse response) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.loadUserByUsername(auth.getName());
        LOGGER.info("token : "+user.getToken());
        return "logged in user..........";
    }

    @PreAuthorize("hasAnyRole('ADMIN','DONOR')")
    @GetMapping("/members/roles")
    public User securedHello(HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.loadUserByUsername(auth.getName());
       /* String jjwtToken = jwtService.generateToken(user);
        response.setHeader("Authorization", jjwtToken);
        LOGGER.info("generate token {} ", jjwtToken);
        messageSenderService.send(jjwtToken);*/
        return user;
    }



    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

    @GetMapping("/lucky-word")
    public String showLuckyWord(){
        return "The lucky word is : "+ luckyWord;
    }
}
