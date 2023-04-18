package angema.applications.hoursloader.app.record;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecordException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RecordException(String message) {
        super(message);
    }

}
