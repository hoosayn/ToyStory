package org.ngo.admin.jjwt;

import com.google.gson.Gson;
import org.apache.commons.codec.binary.StringUtils;
import org.ngo.admin.entity.Admin;
import org.ngo.admin.entity.Users;
import org.ngo.admin.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;

    @Autowired
    private UsersRepository usersRepository;

    @SuppressWarnings("unused")
    public JwtAuthenticationProvider(){
        this(null);
    }

    public JwtAuthenticationProvider(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String user = jwtService.verify((String)authentication.getCredentials());
        return new AdminProfile();  // we define the roles in this class we expect the user must have access to the resource
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthToken.class.equals(authentication);
    }
}
