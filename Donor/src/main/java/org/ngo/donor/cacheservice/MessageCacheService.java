package org.ngo.donor.cacheservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ngo.donor.communication.interservice.MessageSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageCacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageCacheService.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MessageSenderService messageSenderService;

    public void process(String token){
        // save  the token in the cache -- here implement Redis
        messageSenderService.send(token); // send the acknowledgement to the respective microservice as direct message
    }
}
