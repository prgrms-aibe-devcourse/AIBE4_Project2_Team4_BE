package org.aibe4.dodeul.global.dto;

import org.springframework.http.HttpStatus;

public interface BaseCode {
    HttpStatus getHttpStatus();

    String getMessage();
}
