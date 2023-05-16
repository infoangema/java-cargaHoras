package angema.applications.hoursloader.app.administration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AdministracionException extends RuntimeException {

    public AdministracionException(String message){
        super(message);
    }
}
