package org.ngo.registration.core.services.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.ngo.registration.common.util.TheToyStoryUtils;

@Data
@AllArgsConstructor()
public class TheToyStoryException extends RuntimeException {
    private static final long serilaVersionUID = 1L;
    private String errorKey;
    private int errorCode;

    public TheToyStoryException(TheToyStoryUtils.Constants.Errors errors) {
        this.errorCode = TheToyStoryUtils.Constants.Errors.GENERAL_INTERNAL_ERROR.getErrorCode();
        this.errorKey = TheToyStoryUtils.Constants.Errors.GENERAL_INTERNAL_ERROR.getErrorKey();
    }
}
