package org.ngo.registration.core.services;

import org.springframework.stereotype.Component;

/*SingleTon class for secret key*/

@Component
public class SecretKeyProvider {

    private final String SECRETEKEY = "dieschwarzeflagge";
    public byte[] byteSecretKey = null;
    private static SecretKeyProvider _instance;

    private SecretKeyProvider(){
        byteSecretKey = SECRETEKEY.getBytes();
    }

    //For lazy initialization
    public static synchronized SecretKeyProvider getInstance(){
        if(_instance == null){
            _instance = new SecretKeyProvider();
        }
        return _instance;
    }
}
