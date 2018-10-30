package org.ngo.donor.controller;

import org.ngo.donor.entity.Donor;
import org.ngo.donor.exception.NgoExceptions;
import org.ngo.donor.repository.DonorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DonorController {
    private Logger LOGGER = LoggerFactory.getLogger(DonorController.class);


    @Autowired
    private DonorRepository donorRepository;

    @GetMapping("/admin")
    public String test(){
        return "success access";
    }

    @Cacheable(value = "tokens", key = "#userid", unless = "#result.donated_count < 1")
    @GetMapping("/public/{userid}")
    public Donor getDonors(@PathVariable String userid){
        LOGGER.info("fetching donor with id : {}.", userid);
        return donorRepository.findById(Long.valueOf(userid))
                .orElseThrow(() -> new NgoExceptions("donor not found"));
    }

}
