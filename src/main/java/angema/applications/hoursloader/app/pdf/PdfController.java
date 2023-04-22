package angema.applications.hoursloader.app.pdf;

import angema.applications.hoursloader.app.record.RecordDto;
import angema.applications.hoursloader.app.record.RecordRepository;
import angema.applications.hoursloader.app.record.RecordService;
import angema.applications.hoursloader.core.exceptions.ExceptionService;
import angema.applications.hoursloader.core.globalResponse.GlobalResponse;
import angema.applications.hoursloader.core.globalResponse.GlobalResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pdf")
public class PdfController {
    @Autowired
    private ExceptionService exceptionService;

    @Autowired
    private GlobalResponseService globalResponseService;

    @Autowired
    private PdfService pdfService;

    @GetMapping("/send-email")
    public GlobalResponse sendEmail() {
        String res = pdfService.sendEmailByUserId(4L);
        return globalResponseService.responseOK(res);
    }

}
