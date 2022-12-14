package com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions;

import com.backend.sketchbrain.sketchbrainbackend.global.error.ArgumentError;
import lombok.Getter;

import java.util.List;

@Getter
public class ResultExceptions extends RuntimeException {

    private final ResultErrorCodeImpl errorCode;
    private List<ArgumentError> errors;

    public ResultExceptions(ResultErrorCodeImpl errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ResultExceptions(String message, ResultErrorCodeImpl errorCode){
        super(message);
        this.errorCode=errorCode;
    }

    public ResultExceptions(ResultErrorCodeImpl errorCode, List<ArgumentError> errors){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errors = errors;
    }
}
