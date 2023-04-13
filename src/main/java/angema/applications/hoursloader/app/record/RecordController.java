package angema.applications.hoursloader.app.record;

import angema.applications.hoursloader.app.company.CompanyDto;
import angema.applications.hoursloader.app.company.CompanyService;
import angema.applications.hoursloader.core.exceptions.ExceptionService;
import angema.applications.hoursloader.core.globalResponse.GlobalResponse;
import angema.applications.hoursloader.core.globalResponse.GlobalResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private ExceptionService exceptionService;

    @Autowired
    private GlobalResponseService globalResponseService;
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse create(@Valid @RequestBody RecordDto data, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
        RecordDto recordDto = recordService.saveRecord(data);
        return globalResponseService.responseOK(recordDto);
    }

    @GetMapping("/read")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GlobalResponse read() {
        List<RecordDto> recordDtos = recordService.getAllRecord();
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
        data.id = id;

        return globalResponseService.responseOK(data);
    }
}
