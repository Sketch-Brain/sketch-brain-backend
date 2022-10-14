package com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getHttpStatus();
    String name();
    String getMessage();
}
