package angema.applications.hoursloader.app.record;

import angema.applications.hoursloader.app.company.CompanyRepository;
import angema.applications.hoursloader.app.pdf.PdfService;
import angema.applications.hoursloader.app.project.ProjectRepository;
import angema.applications.hoursloader.app.user.UserDto;
import angema.applications.hoursloader.app.user.UserService;
import angema.applications.hoursloader.core.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/records")
public class RecordController {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private RecordService recordService;


    @Autowired
    private UserService userService;


    @Autowired
    JavaMailSender emailSender;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private DateUtil dateUtil;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RecordDto create(@RequestBody RecordDto data) {
        RecordDto recordDto = recordService.saveRecord(data);
        return recordDto;
    }

    @PostMapping("/create/by-list")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_DEVS')")
    public List<String> createAll(@RequestBody List<RecordDto> data) {
        List<String> recordDto = recordService.saveAllRecord(data);
        return recordDto;
    }
    @PostMapping("/create/by-user-id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_DEVS')")
    public List<RecordDto> createByUserId(@RequestBody RecordDto data, @PathVariable("id") Long id) {
        recordService.isValidRecord(data, id);
        recordService.saveRecord(data);
        List<RecordDto> lista = recordService.getAllRecordsByUserId(id);
        return lista;
    }

    @GetMapping("/read")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<RecordDto> read() {
        List<RecordDto> recordDtos = recordService.getAllRecord();
        return recordDtos;
    }

    @GetMapping("/read/{userId}/find-missing-days-for-current-user")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_DEVS')")
    public List<Date> read(@PathVariable("userId") Long userId) {
//        Integer count = recordService.getCountRecordsByUserId(userId);
        List<Date> dateList = recordService.getMissingDays(userId);
        return dateList;
    }

    @GetMapping("/read/{userId}/find-records-for-current-user")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_DEVS')")
    public List<RecordDto> readCurrentRecordsByUserId(@PathVariable("userId") Long userId) {
        List<RecordDto> recordList = recordService.findRecordsDtoByCurrentMonth(userId);
        return recordList;
    }


    @GetMapping("/read/{userId}/record-by-user-id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RecordDto readByUserId(@PathVariable("userId") Long userId) {
        RecordDto recordDto = recordService.getRecordDtoById(userId);
        return recordDto;
    }
    @GetMapping("/read/{userId}/record-list-by-user-id")
    @PreAuthorize("hasRole('ROLE_DEVS') || hasRole('ROLE_ADMIN')")
    public List<RecordDto> readListByUserId(@PathVariable("userId") Long userId) {
        List<RecordDto> recordDto = recordService.getRecordDtoByUserId(userId);
        return recordDto;
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") Long id) {
        RecordDto recordDto = recordService.getRecordDtoById(id);
        recordService.deleteRecord(recordDto);
        return "DELETE";
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RecordDto update(@RequestBody RecordDto data, @PathVariable("id") Long id) {
        RecordDto recordDto = recordService.getRecordDtoById(id);
        data.id = recordDto.id;
        data = recordService.saveRecord(data);
        return data;
    }


    @DeleteMapping("/delete/by-user-id/{userId}/record-id/{recorId}")
    @PreAuthorize("hasRole('ROLE_DEVS')")
    public RecordResponse deleteByUserId(@PathVariable("userId") Long userId, @PathVariable("recorId") Long recorId) {
        RecordDto recordDto = recordService.getRecordDtoByUserIdAndRecorId(userId, recorId);
        recordService.deleteRecord(recordDto);

        return new RecordResponse("DELETE");
    }
    @GetMapping(value = "/download-pdf-by-user-id/{userId}", produces = "application/pdf")
    @PreAuthorize("hasRole('ROLE_DEVS') || hasRole('ROLE_ADMIN')")
    public byte[] downloadPdf(@PathVariable long userId) {
        UserDto user = userService.getUserDtoById(userId);
        List<RecordDto> recordDtoList = recordService.findRecordsDtoByCurrentMonth(userId);
        byte[] pdf = pdfService.createInforme(user, recordDtoList);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename("informe.pdf").build());
        headers.setContentLength(pdf.length);

        return pdf;
    }

    @GetMapping("/send-email-by-user-id/{userId}")
    @PreAuthorize("hasRole('ROLE_DEVS') || hasRole('ROLE_ADMIN')")
    public String sendEmail(@PathVariable long userId) {
        UserDto user = userService.getUserDtoById(userId);
        List<RecordDto> recordDtoList = recordService.getRecordDtoByUserId(userId);
        Long companyId = projectRepository.findCompanyByProjectId(recordDtoList.get(0).project.id);
        List<String> emails = recordService.findEmailsById(companyId);
        String msg = "Angema - " + recordDtoList.get(0).project.description + " - " + dateUtil.getLastMonthWithYearString(recordDtoList.get(0).date);
        String res = pdfService.sendEmailByUser(user, recordDtoList, emails, msg);
        return res;
    }

}
