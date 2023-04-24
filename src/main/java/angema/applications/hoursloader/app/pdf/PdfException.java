package angema.applications.hoursloader.app.pdf;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PdfException extends RuntimeException {

    public PdfException(String message){
        super(message);
    }
}
