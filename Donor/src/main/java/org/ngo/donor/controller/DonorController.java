package org.ngo.donor.controller;

import org.ngo.donor.entity.Donor;
import org.ngo.donor.exception.NgoExceptions;
import org.ngo.donor.repository.DonorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donor/api/v1.0")
public class DonorController {
    private Logger LOGGER = LoggerFactory.getLogger(DonorController.class);


    @Autowired
    private DonorRepository donorRepository;

    @GetMapping("/home/{userid}")
    public String home(@PathVariable String userid){

        LOGGER.info("loading home page for userid {}.",userid);
        return "Hello "+donorRepository.findById(Long.valueOf(userid))
                .orElseThrow(() -> new NgoExceptions("no record fetched")).getSub() +"!";
    }

    @PutMapping("/donate/editAddress/{userid}")
    public String editAddress(@PathVariable String userid, @PathVariable String address){

        donorRepository.findById(Long.valueOf(userid))
                .orElseThrow(() -> new NgoExceptions("user not found"))
                .setAddress(address);
        return "Address update successfull";
    }

    @PostMapping("/donate")
    public Donor donate(@RequestBody Donor donor){

        donorRepository.save(donor);
        return donor;
    }

    @Cacheable(value = "tokens", key = "#userid", unless = "#result.count < 1")
    @GetMapping("/public/{userid}")
    public Donor getDonors(@PathVariable String userid){
        LOGGER.info("fetching donor with id : {}.", userid);
        return donorRepository.findById(Long.valueOf(userid)).get();
    }

}
