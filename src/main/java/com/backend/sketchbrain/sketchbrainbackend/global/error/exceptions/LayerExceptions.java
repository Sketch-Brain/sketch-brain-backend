package com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions;

import com.backend.sketchbrain.sketchbrainbackend.global.error.ArgumentError;
import lombok.Getter;

import java.util.List;

@Getter
public class LayerExceptions extends RuntimeException {

    private final LayerErrorCodeImpl errorCode;
    private List<ArgumentError> errors;

    public LayerExceptions(LayerErrorCodeImpl errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public LayerExceptions(String message, LayerErrorCodeImpl errorCode){
        super(message);
        this.errorCode=errorCode;
    }

    public LayerExceptions(LayerErrorCodeImpl errorCode, List<ArgumentError> errors){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errors = errors;
    }

}
