package angema.applications.hoursloader.core.globalResponse;

import angema.applications.hoursloader.app.pdf.PdfException;
import angema.applications.hoursloader.app.record.RecordException;
import angema.applications.hoursloader.app.user.UserException;
import angema.applications.hoursloader.core.auth.AuthException;
import angema.applications.hoursloader.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
@Order(-2)
public class GlobalResponseWrapperAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private GlobalResponseService globalResponseService;


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getMethod().getName().contains("Exception");
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return globalResponseService.responseOK(body);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> authException(AuthException ex) {
        GlobalResponse response = new GlobalResponse();

        response.status = HttpStatus.UNAUTHORIZED;
        response.path = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
        response.timestamp = dateUtil.getDateString();
        response.body = null;
        response.error = ex.getMessage();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

        //GlobalResponse response = globalResponseService.badRequestResponse(ex.getMessage(), request );

    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> userException(UserException ex) {
        GlobalResponse response = new GlobalResponse();

        response.status = HttpStatus.BAD_REQUEST;
        response.path = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
        response.timestamp = dateUtil.getDateString();
        response.body = null;
        response.error = ex.getMessage();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        //GlobalResponse response = globalResponseService.badRequestResponse(ex.getMessage(), request );

    }


    @ExceptionHandler(PdfException.class)
    public ResponseEntity<?> userException(PdfException ex) {
        GlobalResponse response = new GlobalResponse();

        response.status = HttpStatus.BAD_REQUEST;
        response.path = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
        response.timestamp = dateUtil.getDateString();
        response.body = null;
        response.error = ex.getMessage();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        //GlobalResponse response = globalResponseService.badRequestResponse(ex.getMessage(), request );

    }

    @ExceptionHandler(RecordException.class)
    public ResponseEntity<?> authException(RecordException ex) {
        GlobalResponse response = new GlobalResponse();

        response.status = HttpStatus.BAD_REQUEST;
        response.path = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
        response.timestamp = dateUtil.getDateString();
        response.body = null;
        response.error = ex.getMessage();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        //GlobalResponse response = globalResponseService.badRequestResponse(ex.getMessage(), request );

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex) {
        GlobalResponse response = new GlobalResponse();
        response.status = HttpStatus.NOT_FOUND;
        response.path = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
        response.timestamp = dateUtil.getDateString();
        response.body = null;
        response.error = ex.getMessage();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex) {
        GlobalResponse response = new GlobalResponse();
        response.status = HttpStatus.INTERNAL_SERVER_ERROR;
        response.path = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
        response.timestamp = dateUtil.getDateString();
        response.body = null;
        response.error = ex.getMessage();
        if(ex.getMessage().contains("Access is denied")){
            response.status = HttpStatus.FORBIDDEN;;
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
