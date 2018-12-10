package org.ngo.registration.core.services;

import lombok.extern.slf4j.Slf4j;
import org.ngo.registration.core.repository.UserRepository;
import org.ngo.registration.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service @Transactional
@Slf4j
public class UserService implements UserDetailsService {


    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    /*the username and password is checked by spring security behind the scene */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        List<GrantedAuthority> authorities = userRepository.getRoles(user.getUsername())
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
        user.setAuthorities(authorities);
        LOGGER.info("logging user", user.getUsername());
        return user;
    }

    public User save(User user){
        passwordEncoder.encode(user.getPassword());
        return userRepository.save(user);
    }
}
