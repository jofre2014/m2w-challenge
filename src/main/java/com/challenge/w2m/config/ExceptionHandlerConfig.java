package com.challenge.w2m.config;

import com.challenge.w2m.dto.ErrorDto;
import com.challenge.w2m.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorDto> notFoundException(NotFoundException exception){
        return generateBody(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ServerException.class)
    public ResponseEntity<ErrorDto> serverException(ServerException exception){
        return generateBody(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = AuthorityNotFoundException.class)
    public ResponseEntity<ErrorDto> authorityNotFoundException(AuthorityNotFoundException exception){
        return generateBody(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = JsonDeserializationException.class)
    public ResponseEntity<ErrorDto> jsonDeserealizationException(JsonSerializationException exception){
        return generateBody(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = JsonSerializationException.class)
    public ResponseEntity<ErrorDto> jsonSerializationException(JsonSerializationException exception){
        return generateBody(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private ResponseEntity<ErrorDto> generateBody(Exception exception, HttpStatus statusCode) {

        return new ResponseEntity<>(
            ErrorDto.builder()
                    .status(String.valueOf(statusCode.value()))
                    .message(exception.getMessage())
                    .build(),
                statusCode

        );
    }


}
