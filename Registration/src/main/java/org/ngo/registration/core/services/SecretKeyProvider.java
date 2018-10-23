package org.ngo.registration.core.services;

import org.springframework.stereotype.Component;

@Component
public class SecretKeyProvider {

    private final String SECRETEKEY = "dieschwarzeflagge";

    public byte[] secreteKey(){
        return SECRETEKEY.getBytes();
    }
}
