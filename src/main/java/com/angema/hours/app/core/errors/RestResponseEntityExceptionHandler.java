package com.angema.hours.app.core.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value= {Exception.class, IllegalStateException.class})
    protected Object handleConflict(ResponseStatusException ex, WebRequest request) {

        Error err = new Error();
        err.message.add(ex.getReason());
        err.status = String.valueOf(ex.getRawStatusCode());
        err.path = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        err.timestamp = LocalDateTime.now().format(formatter);
        log.error(">>> " + err.toString());
        return err;
    }
}
