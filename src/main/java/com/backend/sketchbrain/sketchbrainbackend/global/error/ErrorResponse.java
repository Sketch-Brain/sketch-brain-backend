package com.backend.sketchbrain.sketchbrainbackend.global.error;

import com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions.CommonErrorCodeImpl;
import com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions.LayerErrorCodeImpl;
import com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions.ResultErrorCodeImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private HttpStatus code;
    private String message;
    private List<ArgumentError> errors;

    public ErrorResponse(CommonErrorCodeImpl commonErrorCode, String message){
        this.code = commonErrorCode.getHttpStatus();
        this.message = message;
        this.errors = new ArrayList<>();
    }
    public ErrorResponse(CommonErrorCodeImpl commonErrorCode, List<ArgumentError> argumentErrors) {
        this.code = commonErrorCode.getHttpStatus();
        this.message = commonErrorCode.getMessage();
        this.errors = argumentErrors;
    }

    public ErrorResponse(ResultErrorCodeImpl trainingErrorCode, String message) {
        this.code = trainingErrorCode.getHttpStatus();
        this.message = message;
        this.errors = new ArrayList<>();
    }

    public ErrorResponse(ResultErrorCodeImpl resultErrorCode, String message, List<ArgumentError> argumentErrors) {
        this.code = resultErrorCode.getHttpStatus();
        this.message = message;
        this.errors = argumentErrors;
    }

    public ErrorResponse(LayerErrorCodeImpl layerErrorCode, String message) {
        final String value = layerErrorCode.getMessage();
        this.code = layerErrorCode.getHttpStatus();
        this.message = message;
        this.errors = new ArrayList<>();
    }

    public ErrorResponse(LayerErrorCodeImpl layerErrorCode, String message, List<ArgumentError> argumentErrors) {
        final String value = layerErrorCode.getMessage();
        this.code = layerErrorCode.getHttpStatus();
        this.message = message;
        this.errors = argumentErrors;
    }

    public static ErrorResponse create(MethodArgumentTypeMismatchException e){
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        List<ArgumentError> argumentErrors = new ArrayList<>();
        argumentErrors.add(new ArgumentError(e.getName(),value,e.getErrorCode()));
        return new ErrorResponse(CommonErrorCodeImpl.INVALID_ARGUMENT_TYPE, argumentErrors);
    }

}