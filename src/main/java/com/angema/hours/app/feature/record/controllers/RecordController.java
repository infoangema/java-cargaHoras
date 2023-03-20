package com.angema.hours.app.feature.record.controllers;

import com.angema.hours.app.core.exceptions.ExceptionService;
import com.angema.hours.app.feature.record.models.Record;
import com.angema.hours.app.feature.record.services.RecordService;
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

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Record>> getAll() {
        List<Record> record = recordService.getAllRecord();
        return ResponseEntity.ok().body(record);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Record> getId(@PathVariable("id") Long id) {
        Record record = recordService.getIdRecord(id);
        return ResponseEntity.ok().body(record);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Record> save(@RequestBody Record data, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
        Record record = recordService.saveRecord(data);
        return ResponseEntity.ok().body(record);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Record> delete(@PathVariable("id") Long id) {
        Record record = recordService.getIdRecord(id);
        recordService.deleteRecord(record);
        return ResponseEntity.ok().body(record);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Record> update(@Valid @RequestBody Record data, @PathVariable("id") Long id, BindingResult bindingResult) {
        exceptionService.collectErrorsBindings(bindingResult);
        Record record = recordService.getIdRecord(id);
        record.setDate(data.getDate());
        record.setHours(data.getHours());
        record.setDescription(data.getDescription());
        record.setUser(data.getUser());
        record.setProject(data.getProject());
        Record userUpdated = recordService.saveRecord(record);
        return ResponseEntity.ok().body(userUpdated);
    }
}
