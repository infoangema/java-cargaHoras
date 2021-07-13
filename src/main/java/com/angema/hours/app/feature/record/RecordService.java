package com.angema.hours.app.feature.record;

import com.angema.hours.app.core.Messages;
import com.angema.hours.app.feature.project.Project;
import com.angema.hours.app.feature.project.ProjectRepository;
import com.angema.hours.app.feature.user.User;
import com.angema.hours.app.feature.user.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public List<Record> getAllRecord() {
        try {
            return recordRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Record getIdRecord(final Long id) {
        Optional<Record> record = recordRepository.findById(id);
        if (record.isPresent()) {
            return record.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_RECORD_NOT_FOUND);
        }
    }

    public Record saveRecord(Record record) {
        Optional<User> dataUser = userRepository.findById(record.getUser().getId());
        if (!dataUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_NOT_EXISTS_USER);
        }
        Optional<Project> dataProject = projectRepository.findById(record.getProject().getId());
        if (!dataProject.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_NOT_EXISTS_PROJECT);
        }
        try {
            recordRepository.save(record);
            record.setUser(dataUser.get());
            record.setProject(dataProject.get());
            return record;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public void deleteRecord(Record record) {
        try {
            recordRepository.delete(record);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_RECORD_NOT_FOUND, e);
        }
    }
}
