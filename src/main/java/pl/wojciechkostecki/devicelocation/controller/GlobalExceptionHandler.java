package pl.wojciechkostecki.devicelocation.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.wojciechkostecki.devicelocation.exception.LoginAlreadyUsedException;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(LoginAlreadyUsedException.class)
    public final ResponseEntity<Object> handleLoginAlreadyUsedException
            (LoginAlreadyUsedException exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.CONFLICT;
        return handleExceptionInternal(exception, null, headers, status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<Object> handleEntityNotFoundException
            (EntityNotFoundException exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.NOT_FOUND;
        return handleExceptionInternal(exception, null, headers, status, request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Object> handleBadCredentialsException
            (BadCredentialsException exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return handleExceptionInternal(exception, null, headers, status, request);
    }
}
