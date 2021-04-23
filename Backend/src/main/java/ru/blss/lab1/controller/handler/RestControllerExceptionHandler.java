package ru.blss.lab1.controller.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.blss.lab1.exception.*;

import javax.security.auth.login.LoginException;

@ControllerAdvice
public class RestControllerExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<Object> handleLoginException(Exception e) {
        return new ResponseEntity<>("Incorrect username or password", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({NoSuchResourceException.class, CartItemNotFoundException.class, OrderNotFoundException.class})
    public ResponseEntity<Object> handleNoSuchResourceOrCartItemNotFoundException(Exception e) {
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

    @ExceptionHandler(CourierAlreadyExistException.class)
    public ResponseEntity<Object> handleCourierAlreadyExistException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoPermissionException.class)
    public ResponseEntity<Object> handleNoPermissionException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException() {
        // Nothing to do
    }
}
