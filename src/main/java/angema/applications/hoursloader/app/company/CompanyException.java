package angema.applications.hoursloader.app.company;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CompanyException extends RuntimeException {

    public CompanyException(String message){
        super(message);
    }
}
