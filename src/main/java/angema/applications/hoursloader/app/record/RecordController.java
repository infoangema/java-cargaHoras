package angema.applications.hoursloader.app.record;

import angema.applications.hoursloader.core.exceptions.ExceptionService;
import angema.applications.hoursloader.core.globalResponse.GlobalResponse;
import angema.applications.hoursloader.core.globalResponse.GlobalResponseService;
import com.sun.xml.bind.v2.TODO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

}
