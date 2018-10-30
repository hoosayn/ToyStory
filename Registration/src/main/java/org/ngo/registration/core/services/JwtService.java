package org.ngo.registration.core.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.ngo.registration.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${jwt.token.expiration.time}")
    private long tokenExpiration;

    public String generateToken(User user){

        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + tokenExpiration;

        Claims claims = Jwts.claims()
                .setSubject(user.getUsername());
        claims.put("userid", String.valueOf(user.getId()));
        claims.put("role", "ADMIN");
        claims.setExpiration(new Date(expMillis));
       return Jwts.builder()
               .setClaims(claims)
               .setIssuer(ISSUER)
               .signWith(SignatureAlgorithm.HS256, secretKeyProvider.secreteKey())
               .compact();
    }
}
