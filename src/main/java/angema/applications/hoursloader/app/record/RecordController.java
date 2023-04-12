package angema.applications.hoursloader.app.record;

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
        data = recordService.saveRecord(data);
        return globalResponseService.responseOK(data);
    }

    @GetMapping()
    //@PreAuthorize("hasRole('ADMIN')")
    public GlobalResponse getAll() {
        List<Record> record = recordService.getAllRecord();
        return globalResponseService.responseOK(record);
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public GlobalResponse getId(@PathVariable("id") Long id) {
        Record record = recordService.getIdRecord(id);
        return globalResponseService.responseOK(record);
    }


    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public GlobalResponse delete(@PathVariable("id") Long id) {
        Record record = recordService.getIdRecord(id);
        recordService.deleteRecord(record);
        return globalResponseService.responseOK(record);
    }

    @PostMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public GlobalResponse update(@Valid @RequestBody RecordDto data, @PathVariable("id") Long id, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
//        Record record = recordService.getIdRecord(id);
//        record.setDate(data.getDate());
//        record.setHours(data.getHours());
//        record.setDescription(data.getDescription());
//        record.setUser(data.getUser());
//        record.setProject(data.getProject());
//        Record userUpdated = recordService.saveRecord(record);
        return globalResponseService.responseOK(data);
    }
}
