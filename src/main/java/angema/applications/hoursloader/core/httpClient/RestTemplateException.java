package angema.applications.hoursloader.core.httpClient;

import angema.applications.hoursloader.core.globalResponse.GlobalResponse;

public class RestTemplateException extends RuntimeException{

    GlobalResponse globalResponse;
    public RestTemplateException(String message) {
        super(message);
    }
    public RestTemplateException(GlobalResponse globalResponse) {
        super(globalResponse.error);
        this.globalResponse = globalResponse;
    }
}
