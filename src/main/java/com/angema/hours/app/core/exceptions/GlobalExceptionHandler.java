package com.angema.hours.app.core.exceptions;

import com.angema.hours.app.core.auth.AuthException;
import com.angema.hours.app.core.globalResponse.GlobalResponse;
import com.angema.hours.app.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
@Order(-2)
public class GlobalExceptionHandler {

    @Autowired
    private DateUtil dateUtil;

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> authException(AuthException ex, WebRequest request) {
        GlobalResponse response = new GlobalResponse();
        response.status = HttpStatus.UNAUTHORIZED;
        response.path = request.getDescription(false);
        response.timestamp = dateUtil.getDateString();
        response.body = null;
        response.error = ex.getMessage();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        GlobalResponse response = new GlobalResponse();
        response.status = HttpStatus.NOT_FOUND;
        response.path = request.getDescription(false);
        response.timestamp = dateUtil.getDateString();
        response.body = null;
        response.error = ex.getMessage();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        GlobalResponse response = new GlobalResponse();
        response.status = HttpStatus.INTERNAL_SERVER_ERROR;
        response.path = request.getDescription(false);
        response.timestamp = dateUtil.getDateString();
        response.body = null;
        response.error = ex.getMessage();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
