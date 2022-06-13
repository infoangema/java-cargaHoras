package com.angema.hours.app.core.globalResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GlobalResponseService {

    public GlobalResponse response(Object obj, String uri) {
        GlobalResponse response = new GlobalResponse();
        response.body = obj;
        response.status = HttpStatus.OK;
        response.path = uri;
        response.error = null;
        response.timestamp = new Date();
        return response;
    }
}
