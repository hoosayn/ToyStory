package org.ngo.donor.jjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtService {

    @Autowired
    SecretKeyProvider secretKeyProvider;

    public Optional<String> verify(String token){
        byte[] secretKey = secretKeyProvider.secreteKey();
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return Optional.of(claims.getBody().toString());
    }
}
