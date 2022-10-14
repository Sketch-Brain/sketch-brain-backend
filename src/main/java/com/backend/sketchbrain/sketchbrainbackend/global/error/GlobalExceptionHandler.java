package com.backend.sketchbrain.sketchbrainbackend.global.error;

import com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions.CommonErrorCodeImpl;
import com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions.ResultExceptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Http Message 를 Converte 하거나, Binding error 가 발생할 경우.
     * @RequestBody binding 에러도 여기.
     * @param e MethodArgumentNotValidException
     * @return ResponseEntity<>(ErrorResponse,HttpStatus)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentException(MethodArgumentNotValidException e){

        final ErrorResponse response = new ErrorResponse(CommonErrorCodeImpl.INVALID_PARAMETER,e.getMessage());
        return new ResponseEntity<>(response,CommonErrorCodeImpl.INVALID_PARAMETER.getHttpStatus());
    }

    /**
     * Argument Type 이 서로 일치하지 않는 경우에 대한 Exception handling.
     * @param e : MethodArgumentTypeMismatchException
     * @return ResponseEntity<>(ErrorResponse,HttpStatus)
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgsTypeMismatchException(MethodArgumentTypeMismatchException e){

        final ErrorResponse errorResponse = ErrorResponse.create(e);
        return new ResponseEntity<>(errorResponse,CommonErrorCodeImpl.INVALID_ARGUMENT_TYPE.getHttpStatus());
    }

    /**
     * 허가되지 않는 Method Type 으로 요청한 경우에 대한 Exception Handling.
     * @param e HttpRequestMethodNotSupportedException
     * @return ResponseEntity<>(ErrorResponse,HttpStatus)
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleRequestHttpMethodNotSupportException(HttpRequestMethodNotSupportedException e){

        final ErrorResponse errorResponse = new ErrorResponse(CommonErrorCodeImpl.METHOD_NOT_ALLOWED,CommonErrorCodeImpl.METHOD_NOT_ALLOWED.getMessage());
        return new ResponseEntity<>(errorResponse, CommonErrorCodeImpl.METHOD_NOT_ALLOWED.getHttpStatus());
    }

    /**
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ResultExceptions.class)
    protected ResponseEntity<ErrorResponse> handleResultParameterException(ResultExceptions e){
        final ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(),e.getMessage());
        return new ResponseEntity<>(errorResponse, e.getErrorCode().getHttpStatus());
    }

}
