package org.ngo.registration.configuration.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ngo.registration.common.util.TheToyStoryUtils;
import org.ngo.registration.core.services.exceptions.TheToyStoryException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Throwable causeException = authException.getCause();
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        TheToyStoryException theToyStoryException;

        if(causeException instanceof TheToyStoryException) {
            theToyStoryException = (TheToyStoryException) causeException;
        }else {
            theToyStoryException = new TheToyStoryException(TheToyStoryUtils.Constants.Errors.WRONG_USERNAME_OR_PASSWORD);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String responseJson = objectMapper.writeValueAsString(theToyStoryException);
        response.getOutputStream().println(responseJson);
        response.getOutputStream().flush();
    }
}
