package com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LayerErrorCodeImpl implements ErrorCode{

    UNKNOWN_LAYER_NAME_REFERED(HttpStatus.NOT_FOUND,"INCORRECT LAYER NAME");
    private final HttpStatus httpStatus;
    private final String message;
}
