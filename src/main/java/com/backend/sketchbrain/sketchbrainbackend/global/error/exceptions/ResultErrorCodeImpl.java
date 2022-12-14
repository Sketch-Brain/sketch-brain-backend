package com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResultErrorCodeImpl implements ErrorCode{

    UKNOWN_USER_REFERED(HttpStatus.NOT_FOUND,"INCORRECT USER NAME"),
    UKNOWN_ID_REFERED(HttpStatus.NOT_FOUND,"INCORRECT ID"),
    UKNOWN_UUID_REFERED(HttpStatus.NOT_FOUND,"INCORRECT UUID"),
    INCORRECT_PARAMETER_EXIST(HttpStatus.BAD_REQUEST, "INCORRECT PARAMETER EXIST");
    private final HttpStatus httpStatus;
    private final String message;
}
