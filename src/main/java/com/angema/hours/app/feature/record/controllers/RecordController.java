package com.angema.hours.app.feature.record.controllers;

import com.angema.hours.app.core.Messages;
import com.angema.hours.app.feature.record.models.Record;
import com.angema.hours.app.feature.record.services.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping()
    private ResponseEntity<List<Record>> getAll () {
        List<Record> user = recordService.getAllRecord();
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Record> getId (@PathVariable("id") final String id) {
        try {
            Optional<Record> user = recordService.getRecordId(Long.parseLong(id));
            if (user.isPresent()) {
                return ResponseEntity.ok().body(user.get());
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (NumberFormatException e) {
            log.info(Messages.ERROR_IDCHARACTER,id);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping()
    private ResponseEntity<Record> save (@Valid @RequestBody Record data, BindingResult errorValidation) {
        if (errorValidation.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Record user = recordService.saveRecord(data);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Record> delete (@PathVariable("id") final String id) {
        try {
            Optional<Record> user = recordService.getRecordId(Long.parseLong(id));
            if (user.isPresent()) {
                recordService.deleteRecord(user.get());
                return ResponseEntity.ok().body(user.get());
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (NumberFormatException e) {
            log.info(Messages.ERROR_IDCHARACTER,id);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping()
    private ResponseEntity<Record> update (@Valid @RequestBody Record data, BindingResult errorValidation) {
        if (errorValidation.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Optional<Record> user = recordService.getRecordId(data.getId());
            if (user.isPresent()) {
                recordService.updateRecord(data,user.get());
                return ResponseEntity.ok().body(user.get());
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
