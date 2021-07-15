package com.angema.hours.app.core.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ErrorService {

    public Error handleConflict(String errors, HttpStatus status, String path, Object obj) {
        Error err = new Error();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        err.timestamp = LocalDateTime.now().format(formatter);
        err.status = status.toString();
        err.path = path;
        err.message.add(errors);
        err.object = obj;
        log.error(">>> " + err.toString());
        return err;
    }

    public Error listHandleConflict(List<String> errors, HttpStatus status, String path, Object obj) {
        Error err = new Error();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        err.timestamp = LocalDateTime.now().format(formatter);
        err.status = status.toString();
        err.path = path;
        err.message = errors;
        err.object = obj;
        log.error(">>> " + err.toString());
        return err;
    }

    public List<String> collectErrorsBindings(BindingResult bindingResult) {
        List<String> errors = new ArrayList<String>();
        if (bindingResult.hasErrors()) {
            for (FieldError err: bindingResult.getFieldErrors()) {
                errors.add(err.getDefaultMessage());
                log.error(">>> " + err.toString());
            }
        }
        return errors;
    }
}
