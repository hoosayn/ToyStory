package org.ngo.donor;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.ngo.donor.cacheservice.MessageCacheService;
import org.ngo.donor.entity.Donor;
import org.ngo.donor.jjwt.JwtService;
import org.ngo.donor.repository.DonorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;

import java.io.StringReader;
import java.util.Optional;

@SpringBootApplication
@EnableBinding(Processor.class)
@EnableCaching
@EnableDiscoveryClient
public class DonorApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(DonorApplication.class);

	@Autowired
	MessageCacheService messageCacheService;
	@Autowired
	JwtService jwtService;
	@Autowired
	DonorRepository donorRepository;

	public static void main(String[] args) {
		SpringApplication.run(DonorApplication.class, args);
	}

	@StreamListener(Processor.INPUT)
	public void receiveToken(String token){

		System.out.println("token received : "+token);
		System.out.println("=========");
//        jwtService.verify(token).get();
        System.out.println(jwtService.verify(token).get());
		Optional<String> userInfo = jwtService.verify(token);
        if (userInfo.get().contains("DONOR")) {
            Gson g = new Gson();
            JsonReader reader = new JsonReader(new StringReader(userInfo.get()));  // to avoid the space in the input data
            reader.setLenient(true);
            Donor donor = g.fromJson(reader, Donor.class);
            donorRepository.save(donor);
        }
        messageCacheService.process(token);
	}

}
