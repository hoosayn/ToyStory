package org.ngo.donor.jjwt;

import com.google.gson.Gson;
import org.ngo.donor.entity.Donor;
import org.ngo.donor.exception.NgoExceptions;
import org.ngo.donor.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;

    @Autowired
    DonorRepository donorRepository;

    @SuppressWarnings("unused")
    public JwtAuthenticationProvider(){
        this(null);
    }

    @Autowired
    public JwtAuthenticationProvider(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            Optional<String> userInfo = jwtService.verify((String) authentication.getCredentials());
            Gson g = new Gson();
            Donor donor = g.fromJson(userInfo.get(), Donor.class);
            Optional<Donor> donorFromDB = donorRepository.findById(donor.getUserid());
            if(!donorFromDB.isPresent()){
                donor.setCount(1L);
                donorRepository.save(donor);
            }
            return new UserProfile(donor.getSub(), donor.getRole());
        }catch (Exception e){
            throw new NgoExceptions("Jwt Authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthToken.class.equals(authentication);
    }
}
