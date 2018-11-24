package org.ngo.registration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableBinding(Processor.class)
@EnableDiscoveryClient
public class RegistrationApplication extends SpringBootServletInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationApplication.class);
    private ObjectMapper mapper = new ObjectMapper();


	public static void main(String[] args) {

		SpringApplication.run(RegistrationApplication.class, args);
	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@StreamListener(Processor.INPUT)
	public void receiveTokenAcknowledgment(String tokenAck) throws JsonProcessingException {
        LOGGER.info("Token reception acknowledgement: {}", mapper.writeValueAsString(tokenAck));
        /*
        token is received by the calling microservice --
        userService.updateUser(service
                .loadUserByUsername(
                        SecurityContextHolder.getContext().getAuthentication().getName()).)*/
    }
}
