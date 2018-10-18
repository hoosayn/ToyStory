package org.ngo.controller;

import org.ngo.core.repository.RoleRepository;
import org.ngo.core.services.JwtService;
import org.ngo.core.services.UserService;
import org.ngo.entity.Role;
import org.ngo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
        user.setRoles(Arrays.asList( roleRepository.findByRolename("ADMIN")));
        User respUser = userService.save(user);
        /*respUser.getRoles().stream().forEach(r -> roleRepository.addRolesToUser(respUser.getId(),
                r.getId()));*/
        response.setHeader("Token", jwtService.generateToken(user));
        return "postcallreturnstoken";
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/members/admin")
    public String securedHello(HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        response.setHeader("Token", jwtService.generateToken(userService.loadUserByUsername(auth.getName())));
        return "Hello Admin";
    }

}
