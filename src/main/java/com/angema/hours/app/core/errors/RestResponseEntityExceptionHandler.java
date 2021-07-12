package com.angema.hours.app.core.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value= {Exception.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(ResponseStatusException ex, WebRequest request) {

        Integer status = ex.getRawStatusCode();
        Error err = new Error();
        err.message = ex.getReason();
        err.status = String.valueOf(status);
        err.path = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        err.timestamp = LocalDateTime.now().format(formatter);
        log.error(">>> " + err.toString());

        return handleExceptionInternal(
                ex,
                err,
                new HttpHeaders(),
                HttpStatus.valueOf(400),
                request
        );
    }
}
