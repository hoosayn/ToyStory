package org.ngo.registration.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.ngo.registration.common.constant.RegistrationConstant;
import org.ngo.registration.core.repository.RoleRepository;
import org.ngo.registration.core.repository.UserRepository;
import org.ngo.registration.core.services.SecretKeyProvider;
import org.ngo.registration.core.services.UserService;
import org.ngo.registration.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	
    private UserService userService;
	
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }
   
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    	
       String token = Arrays.asList(request.getCookies()).stream()
    		   .filter(item -> item.getName().equalsIgnoreCase(RegistrationConstant.COOKIE_SET_HEADER))
    		   .findFirst().get().getValue().replaceAll(RegistrationConstant.ACCESS_TOKEN+"=", "");
       if(token == null){
            chain.doFilter(request, response);
            return;
       }

       UsernamePasswordAuthenticationToken authentication = getAuthentication(request, token);
       SecurityContextHolder.getContext().setAuthentication(authentication);
       chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, String token) {
        if(token != null){
            //parse the token
        	DecodedJWT decodeToken = null;
        	decodeToken = JWT.require(Algorithm.HMAC512(SecretKeyProvider.getInstance().byteSecretKey))
            .build()
            .verify(token);

        	String user = decodeToken.getSubject();
        	Claim claim = decodeToken.getClaim("role");
            if(user != null){
            	User userObj = userService.loadUserByUsername(user);
                return new UsernamePasswordAuthenticationToken(user, null, userObj.getAuthorities());
            }
            return null;
        }
        return null;
    }
}
