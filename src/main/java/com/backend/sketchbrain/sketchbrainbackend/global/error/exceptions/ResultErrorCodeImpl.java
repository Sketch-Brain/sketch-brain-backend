package com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResultErrorCodeImpl implements ErrorCode{

    UKNOWN_USER_REFERED(HttpStatus.NOT_FOUND,"INCORRECT USER NAME"),
    UKNOWN_ID_REFERED(HttpStatus.NOT_FOUND,"INCORRECT ID");
    private final HttpStatus httpStatus;
    private final String message;
}
