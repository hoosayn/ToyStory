package org.ngo.donor.communication.interservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderService {

    @Autowired
    private Source source;

    public boolean send(String token){

        return this.source.output().send(MessageBuilder.withPayload(token).build());

    }

}
