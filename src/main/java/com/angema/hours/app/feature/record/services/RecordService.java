package com.angema.hours.app.feature.record.services;

import com.angema.hours.app.core.Messages;
import com.angema.hours.app.feature.record.models.Record;
import com.angema.hours.app.feature.record.repositories.RecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    public List<Record> getAllRecord() {
        return recordRepository.findAll();
    }

    public Record getIdRecord(final Long id) {
        Optional<Record> record = recordRepository.findById(id);
        if (record.isPresent()) {
            return record.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_USER_NOT_FOUND);
        }
    }

    public Record saveRecord(Record record) {
        return recordRepository.save(record);
    }

    public void deleteRecord(Record record) {
        try {
            recordRepository.delete(record);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_USER_NOT_FOUND, e);
        }
    }
}
