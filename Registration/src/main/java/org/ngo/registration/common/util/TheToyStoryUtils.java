package org.ngo.registration.common.util;

import com.fasterxml.jackson.annotation.JsonValue;

public class TheToyStoryUtils {

    public static class Constants {
        public static enum Errors {
            GENERAL_INTERNAL_ERROR(1, "internal.server.error"),
            WRONG_USERNAME_OR_PASSWORD(2, "wrong.username.password");
            private int errorCode;
            private String errorKey;

            private Errors(int errorCode, String errorKey){
                this.errorCode = errorCode;
                this.errorKey = errorKey;
            }

            @JsonValue
            public int getErrorCode(){ return this.errorCode; }

            @JsonValue
            public String getErrorKey(){ return this.errorKey; }
        }
    }
}
