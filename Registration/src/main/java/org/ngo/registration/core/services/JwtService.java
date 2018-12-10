package org.ngo.registration.core.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.ngo.registration.core.repository.RoleRepository;
import org.ngo.registration.entity.Role;
import org.ngo.registration.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtService {
    private static final String ISSUER = "asak";

    @Autowired
    SecretKeyProvider secretKeyProvider;

    @Autowired
    RoleRepository roleRepository;

    @Value("${jwt.token.expiration.time}")
    private long tokenExpiration;

    public String generateToken(User user){

        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + tokenExpiration;

        Collection<Role> roles = roleRepository.findByRolecode(
                Arrays.asList(user.getRoleType().split(",")))
                .get();
        String roleName = roles.stream().flatMap(r -> Stream.of(r.getRolename()))
                .collect(Collectors.joining(","));
        Claims claims = null;
        if("DONOR".equalsIgnoreCase(roleName)){
            claims = Jwts.claims()
                    .setSubject(user.getUsername());
            claims.put("userid", String.valueOf(user.getId()));
            claims.put("role", roleName);
            claims.put("count",0);
            claims.put("username", user.getUsername());
            claims.put("firstname", user.getFirstName());
            claims.put("address", user.getAddress());
            claims.setExpiration(new Date(expMillis));
        }else if("ADMIN".equalsIgnoreCase(roleName)){
            claims = Jwts.claims()
                    .setSubject(user.getUsername());
            claims.put("userid", String.valueOf(user.getId()));
            claims.put("role", roleName);
            claims.put("firstname", user.getFirstName());
            claims.put("username", user.getUsername());
            claims.setExpiration(new Date(expMillis));
        }
       return Jwts.builder()
               .setClaims(claims)
               .setIssuer(ISSUER)
               .signWith(SignatureAlgorithm.HS256, SecretKeyProvider.getInstance().byteSecretKey)
               .compact();
    }
}
