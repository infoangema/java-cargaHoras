package com.angema.hours.app.feature.record;

import com.angema.hours.app.core.errors.ErrorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private ErrorService errorService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    private List<Record> getAll() {
        return recordService.getAllRecord();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    private Record getId(@PathVariable("id") Long id) {
        return recordService.getIdRecord(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    private Record save(@Valid @RequestBody Record data, BindingResult bindingResult) {
        errorService.collectErrorsBindings(bindingResult);
        return recordService.saveRecord(data);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    private Record delete(@PathVariable("id") Long id) {
        Record record = recordService.getIdRecord(id);
        recordService.deleteRecord(record);
        return record;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    private Record update(@Valid @RequestBody Record data, @PathVariable("id") Long id, BindingResult bindingResult) {
        errorService.collectErrorsBindings(bindingResult);
        Record record = recordService.getIdRecord(id);
        record.setDate(data.getDate());
        record.setHours(data.getHours());
        record.setDescription(data.getDescription());
        record.setUser(data.getUser());
        record.setProject(data.getProject());
        return recordService.saveRecord(record);
    }
}
