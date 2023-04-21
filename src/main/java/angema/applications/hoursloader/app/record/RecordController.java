package angema.applications.hoursloader.app.record;

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
@RequestMapping("/records")
public class RecordController {
    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private RecordService recordService;

    @Autowired
    private ExceptionService exceptionService;

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    private GlobalResponseService globalResponseService;
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse create(@Valid @RequestBody RecordDto data, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
        RecordDto recordDto = recordService.saveRecord(data);
        return globalResponseService.responseOK(recordDto);
    }
    @PostMapping("/create/by-user-id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_DEVS')")
    public GlobalResponse createByUserId(@Valid @RequestBody RecordDto data, @PathVariable("id") Long id, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
        RecordDto recordDto = recordService.saveRecord(data);
        List<RecordDto> lista = recordService.getAllRecordsByUserId(id);
        return globalResponseService.responseOK(lista);
    }

    @GetMapping("/read")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse read() {
        List<RecordDto> recordDtos = recordService.getAllRecord();
//        todo: obtener dias no cargados.
        Integer count = recordRepository.countRecordsByUserIdAndMonth(4L);
        List<Date> dateList = recordRepository.findMissingDatesByUserIdAndMonth(4L);
        return globalResponseService.responseOK(recordDtos);
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse readById(@PathVariable("id") Long id) {
        RecordDto recordDto = recordService.getRecordDtoById(id);
        return globalResponseService.responseOK(recordDto);
    }



    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse delete(@PathVariable("id") Long id) {
        RecordDto recordDto = recordService.getRecordDtoById(id);
        recordService.deleteRecord(recordDto);
        return globalResponseService.responseOK(recordDto);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse update(@Valid @RequestBody RecordDto data, @PathVariable("id") Long id, BindingResult bindingResult) {

        exceptionService.collectErrorsBindings(bindingResult);
        RecordDto recordDto = recordService.getRecordDtoById(id);
        // todo: response error
        data.id = recordDto.id;
        data = recordService.saveRecord(data);
        return globalResponseService.responseOK(data);
    }

    @GetMapping("/read/by-user-id/{id}")
    @PreAuthorize("hasRole('ROLE_DEVS')")
    public GlobalResponse readByUserId(@PathVariable("id") Long id) {
        List<RecordDto> recordDto = recordService.getRecordDtoByUserId(id);
        return globalResponseService.responseOK(recordDto);
    }

    @DeleteMapping("/delete/by-user-id/{userId}/record-id/{recorId}")
    @PreAuthorize("hasRole('ROLE_DEVS')")
    public GlobalResponse deleteByUserId(@PathVariable("userId") Long userId, @PathVariable("recorId") Long recorId) {
        RecordDto recordDto = recordService.getRecordDtoByUserIdAndRecorId(userId, recorId);
        recordService.deleteRecord(recordDto);
        return globalResponseService.responseOK(recordDto);
    }



    @GetMapping("/send-email")
    public void sendEmailWithAttachment() throws MessagingException, MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo("paletgerardo@gmail.com");
        helper.setSubject("info@angema.com.ar");
        helper.setText("hola desde mail.");

        // Agregar un archivo adjunto al correo electrónico
//        FileSystemResource file = new FileSystemResource(new File("ruta/al/archivo.pdf"));
        ClassPathResource file = new ClassPathResource("static/archivo.pdf");

        helper.addAttachment("Archivo.pdf", file);

        emailSender.send(message);
    }

}
