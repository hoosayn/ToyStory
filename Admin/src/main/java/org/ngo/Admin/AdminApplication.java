package org.ngo.admin;

import com.google.gson.Gson;
import org.ngo.admin.communication.interservice.MessageSenderService;
import org.ngo.admin.entity.Users;
import org.ngo.admin.jjwt.JwtService;
import org.ngo.admin.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;


@SpringBootApplication
@EnableBinding(Processor.class)
public class AdminApplication {

    private Logger LOGGER = LoggerFactory.getLogger(AdminApplication.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MessageSenderService messageSenderService;

    @Autowired
    private UsersRepository usersRepository;

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

	@StreamListener(Processor.INPUT)
	public void receiveToken(String token){
        LOGGER.info("token received {}.", token);
        LOGGER.info(jwtService.verify(token));
        String user = jwtService.verify(token);
        if(user != null || !user.isEmpty()){
            if(user.contains("DONOR")){
                Gson g = new Gson();
                Users donor = g.fromJson(user, Users.class);
                usersRepository.save(donor);
            }
        }
        messageSenderService.send("token received, thanks!");
    }
}
