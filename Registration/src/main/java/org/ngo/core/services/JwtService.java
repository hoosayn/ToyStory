package org.ngo.core.services;

import io.jsonwebtoken.SignatureAlgorithm;
import org.ngo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class JwtService {
    private static final String ISSUER = "asak";

    @Autowired
    SecretKeyProvider secretKeyProvider;

    public String generateToken(User user){
        Date expiration = Date.from(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.UTC));
       return Jwts.builder()
               .setSubject(user.getUsername())
               .setExpiration(expiration)
               .setIssuer(ISSUER)
               .signWith(SignatureAlgorithm.HS256, secretKeyProvider.secreteKey())
               .compact();
    }
}
