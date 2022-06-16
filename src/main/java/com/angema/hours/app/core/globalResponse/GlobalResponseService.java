package com.angema.hours.app.core.globalResponse;

import com.angema.hours.app.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GlobalResponseService {

    @Autowired
    private DateUtil dateUtil;

    public GlobalResponse response(Object obj, String uri) {
        GlobalResponse response = new GlobalResponse();
        response.body = obj;
        response.status = HttpStatus.OK;
        response.path = uri;
        response.error = null;
        response.timestamp = dateUtil.getDateString();
        return response;
    }
}
