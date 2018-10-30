package org.ngo.donor;

import org.ngo.donor.cacheservice.MessageCacheService;
import org.ngo.donor.jjwt.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;

@SpringBootApplication
@EnableBinding(Processor.class)
@EnableCaching
public class DonorApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(DonorApplication.class);

	@Autowired
	MessageCacheService messageCacheService;
	@Autowired
	JwtService jwtService;

	public static void main(String[] args) {
		SpringApplication.run(DonorApplication.class, args);
	}

	@StreamListener(Processor.INPUT)
	public void receiveToken(String token){

		System.out.println("token received : "+token);
		System.out.println("=========");
//        jwtService.verify(token).get();
        System.out.println(jwtService.verify(token).get());

		messageCacheService.process(token);
	}

}
