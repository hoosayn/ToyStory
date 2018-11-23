package org.ngo.admin.controller;

import io.swagger.annotations.Api;
import org.ngo.admin.entity.Users;
import org.ngo.admin.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/api/v1.0", produces = "application/json")
@Api(value="adminservice", description = "this is an admin service for donating toys to underprivileged")
public class AdminController {

    private Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/home/{userid}")
    public List<Users> home(@PathVariable String userid){
        LOGGER.info("Loading home page for userid {}.",userid);
        return usersRepository.findAll();
    }

}
