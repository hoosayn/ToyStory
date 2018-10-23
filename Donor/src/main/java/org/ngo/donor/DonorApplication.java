package org.ngo.donor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;

@SpringBootApplication
@EnableBinding(Process.class)
public class DonorApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(DonorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DonorApplication.class, args);
	}

}
