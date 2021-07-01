package com.angema.hours.app.feature.record.services;

import com.angema.hours.app.feature.record.models.Record;
import com.angema.hours.app.feature.record.repositories.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    public List<Record> getAllRecord () {
        return recordRepository.findAll();
    }

    public Optional<Record> getRecordId (final int id) {
        return recordRepository.findById(id);
    }

    public Record saveRecord (Record data) {
        return recordRepository.save(data);
    }

    public void deleteRecord (Record record) {
        recordRepository.delete(record);
    }

    public void updateRecord (Record data, Record record) {
        record.setDate(data.getDate());
        record.setHours(data.getHours());
        record.setDescription(data.getDescription());
        record.setIdUser(data.getIdUser());
        record.setIdProject(data.getIdProject());
        recordRepository.save(record);
    }
}
