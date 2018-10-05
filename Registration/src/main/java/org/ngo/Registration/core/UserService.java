package org.ngo.registration.core;

import lombok.extern.slf4j.Slf4j;
import org.ngo.registration.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @Transactional
@Slf4j
public class UserService  {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }
}
