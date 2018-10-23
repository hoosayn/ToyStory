package org.ngo.registration.controller;

import org.ngo.registration.communication.interservice.MessageSenderService;
import org.ngo.registration.core.repository.RoleRepository;
import org.ngo.registration.core.services.JwtService;
import org.ngo.registration.core.services.UserService;
import org.ngo.registration.entity.Role;
import org.ngo.registration.entity.User;
import org.ngo.registration.expections.NgoExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UserRegistrationController {

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

    @GetMapping(value = "/")
    public ResponseEntity<String> registrationFormSubmit(HttpServletResponse response){
        HttpHeaders  httpHeaders = new HttpHeaders();
        httpHeaders.set("asif","shaikh");

        return new ResponseEntity<>("hello", httpHeaders, HttpStatus.CREATED);
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
        response.setHeader("Token", user.getToken());
        return "postcallreturnstoken";

//        {"id":"2","username":"asif2","password":"a","title":"Mr.","firstName":"asif","lastName":"shaikh"}  json request to register
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/members/admin")
    public String securedHello(HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        response.setHeader("Token", jwtService.generateToken(userService.loadUserByUsername(auth.getName())));
        return "Hello Admin";
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }
}
