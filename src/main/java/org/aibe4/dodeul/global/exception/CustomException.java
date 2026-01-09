package org.aibe4.dodeul.global.exception;

import lombok.Getter;
import org.aibe4.dodeul.global.response.enums.ErrorCode;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String detailMessage;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    public CustomException(ErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
        this.detailMessage = detailMessage;
    }
}
