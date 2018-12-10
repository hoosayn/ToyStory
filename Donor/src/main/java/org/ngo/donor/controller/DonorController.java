package org.ngo.donor.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.servlet.http.HttpServletResponse;

import org.ngo.donor.entity.Donor;
import org.ngo.donor.exception.NgoExceptions;
import org.ngo.donor.repository.DonorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/donor/api/v1.0", produces = "application/json")
@Api(value="donorservice", description = "this is a service for donating toys to underprivileged")
public class DonorController {
    private Logger LOGGER = LoggerFactory.getLogger(DonorController.class);


    @Autowired
    private DonorRepository donorRepository;

    @GetMapping(value = "/")
    public ResponseEntity<String> registrationFormSubmit(HttpServletResponse response){
        HttpHeaders  httpHeaders = new HttpHeaders();
        httpHeaders.set("asif","shaikh");

        return new ResponseEntity<>("hello", httpHeaders, HttpStatus.CREATED);
    }
    
    @GetMapping("/home/{userid}")
    public String home(@PathVariable String userid){

        LOGGER.info("loading home page for userid {}.",userid);
        return "Hello "+donorRepository.findById(Long.valueOf(userid))
                .orElseThrow(() -> new NgoExceptions("no record fetched")).getSub() +"!";
    }

    @PutMapping("/donate/editAddress/{userid}")
    @ApiOperation(value = "Update the Donor Address", notes = "Update the Donor Address",
                    response = String.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully updated the Donor Address"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
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
