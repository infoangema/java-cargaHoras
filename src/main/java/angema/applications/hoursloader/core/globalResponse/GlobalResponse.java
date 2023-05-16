package angema.applications.hoursloader.core.globalResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalResponse<T> {
    public HttpStatus status;
    public String path;
    public String timestamp;
    public T body;
    public String error;

    public GlobalResponse(HttpStatus status, String error) {
        this.status = status;
        this.error = error;
    }
}

