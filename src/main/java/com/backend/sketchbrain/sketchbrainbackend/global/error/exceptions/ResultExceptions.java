package com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions;

import lombok.Getter;

@Getter
public class ResultExceptions extends RuntimeException {

    private final ResultErrorCodeImpl errorCode;

    public ResultExceptions(ResultErrorCodeImpl errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ResultExceptions(String message, ResultErrorCodeImpl errorCode){
        super(message);
        this.errorCode=errorCode;
    }
}
