package org.ngo.admin.jjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    @Autowired
    SecretKeyProvider secretKeyProvider;

    public String verify(String token){
        byte[] secretKey = secretKeyProvider.secreteKey();
        Jws<Claims> claims =  Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return claims.getBody().toString();
    }
}
