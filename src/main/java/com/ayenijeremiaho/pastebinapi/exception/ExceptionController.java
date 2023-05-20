package com.ayenijeremiaho.pastebinapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({AuthenticationException.class, NotFoundException.class, GeneralException.class, Exception.class})
    public final ResponseEntity<?> handleException(Exception ex) {
        ExceptionMessage message = ExceptionMessage.get(ex);

        if (ex instanceof AuthenticationException || ex instanceof BadCredentialsException) {
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        } else if (ex instanceof NotFoundException) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (ex instanceof GeneralException) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public final ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        log.info("Error occurred during request body validation, error message is {}", ex.getMessage());

        //Get all errors
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList().get(0);
//                .collect(Collectors.joining(". "));

        ExceptionMessage message = ExceptionMessage.get(ex.getClass(), errors);
        return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
    }

}
