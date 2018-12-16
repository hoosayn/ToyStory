package org.ngo.registration.configuration.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ngo.registration.common.constant.RegistrationConstant;
import org.ngo.registration.core.repository.RoleRepository;
import org.ngo.registration.core.repository.UserRepository;
import org.ngo.registration.core.services.SecretKeyProvider;
import org.ngo.registration.core.services.UserService;
import org.ngo.registration.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Value("${jwt.token.expiration.time}") String EXPIRATION_TIME;

    private RoleRepository roleRepository;
    
    private UserRepository userRepository;
    
    private UserService userService;
    
    private AuthenticationManager authenticationManager;

   public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, RoleRepository roleRepository) {
    this.authenticationManager = authenticationManager;
    this.userService = userService;
    this.roleRepository = roleRepository;
        
     // By default, UsernamePasswordAuthenticationFilter listens to "/login" path. 
     		// In our case, we use "/auth". So, we need to override the defaults.
//     		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/our-own-path", "POST"));
    }
    
    /*public JWTAuthenticationFilter(UserService userService) {
		this.userService = userService;
	}*/


	@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        /*User cred = new ObjectMapper()
		        .readValue(request.getInputStream(), User.class);*/ 
		return authenticationManager.authenticate(
		        new UsernamePasswordAuthenticationToken(
		        		request.getParameter("username"),
		        		request.getParameter("password"),
		                new ArrayList<>()
		        )
		);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    	User user = (User)authResult.getPrincipal();
        String token = JWT.create()
                .withSubject(((User)authResult.getPrincipal()).getUsername())
                .withClaim("role", roleRepository.findByRolecode(user.getRoleType()).get().getRolename())
                .withExpiresAt(new Date(System.currentTimeMillis() + RegistrationConstant.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecretKeyProvider.getInstance().byteSecretKey));
        user.setToken(token);
        response.addHeader(RegistrationConstant.HEADER_STRING, RegistrationConstant.TOKEN_PREFIX + token);
        Cookie cookie = new Cookie(RegistrationConstant.COOKIE_SET_HEADER, RegistrationConstant.ACCESS_TOKEN+"=" + token);  
        response.addCookie(cookie);
    }
}
