package angema.applications.hoursloader.core.globalResponse;

import angema.applications.hoursloader.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class GlobalResponseService {

    @Autowired
    private DateUtil dateUtil;
    GlobalResponse response = new GlobalResponse();

    public GlobalResponse responseOK(Object obj ) {
        response.body = obj;
        response.status = HttpStatus.OK;
        response.path = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getRequestURI();
        response.error = null;
        response.timestamp = dateUtil.getDateString();
        return response;
    }
}
