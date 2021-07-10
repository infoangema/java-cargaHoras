package com.angema.hours.app.core.errors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ErrorService {

    public void collectErrorsBindings(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (Object object : bindingResult.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldError.getDefaultMessage());
                }
                if(object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al validar los datos de entrada");
                }
            }
        }
    }
}
