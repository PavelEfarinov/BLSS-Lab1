package ru.blss.lab1.controller.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.blss.lab1.exception.NoMoreItemException;
import ru.blss.lab1.exception.NoSuchResourceException;
import ru.blss.lab1.exception.UnauthorizedUserException;
import ru.blss.lab1.exception.ValidationException;

@ControllerAdvice
public class RestControllerExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchResourceException.class)
    public ResponseEntity<Object> handleNoSuchResourceException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<Object> handleUnauthorizedUserException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NoMoreItemException.class)
    public ResponseEntity<Object> handleNoMoreItemException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.GONE);
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException() {
        // Nothing to do
    }
}
