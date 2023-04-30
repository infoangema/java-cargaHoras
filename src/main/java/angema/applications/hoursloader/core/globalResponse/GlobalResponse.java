package angema.applications.hoursloader.core.globalResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public class GlobalResponse {
    public HttpStatus status;
    public String path;
    public String timestamp;
    @JsonProperty("body")
    public Object body;
    public String error;
}
