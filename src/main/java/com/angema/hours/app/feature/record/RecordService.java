package com.angema.hours.app.feature.record;

import com.angema.hours.app.core.Messages;
import com.angema.hours.app.core.errors.ErrorService;
import com.angema.hours.app.feature.project.Project;
import com.angema.hours.app.feature.project.ProjectRepository;
import com.angema.hours.app.feature.user.User;
import com.angema.hours.app.feature.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
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

    @Autowired
    private ErrorService errorService;

    public List<Record> getAllRecord() {
        try {
            return recordRepository.findAll();
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Record getIdRecord(final Long id) {
        try {
            Optional<Record> record = recordRepository.findById(id);
            if (!record.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_RECORD_NOT_FOUND);
            }
            return record.get();
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Record saveRecord(Record record, BindingResult bindingResult) {
        try {
            List<String> errors = errorService.collectErrorsBindings(bindingResult);
            if (!errors.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Arrays.deepToString(errors.toArray()));
            }
            Optional<User> user = userRepository.findById(record.getUser().getId());
            if (!user.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.ERROR_NOT_EXISTS_USER);
            }
            Optional<Project> project = projectRepository.findById(record.getProject().getId());
            if (!project.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.ERROR_NOT_EXISTS_PROJECT);
            }
            record.setUser(user.get());
            record.setProject(project.get());
            return recordRepository.save(record);
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Record deleteRecord(final Long id) {
        try {
            Optional<Record> record = recordRepository.findById(id);
            if (!record.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_RECORD_NOT_FOUND);
            }
            recordRepository.delete(record.get());
            return record.get();
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Record updateRecord(Record data, final Long id, BindingResult bindingResult) {
        try {
            List<String> errors = errorService.collectErrorsBindings(bindingResult);
            if (!errors.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Arrays.deepToString(errors.toArray()));
            }
            Optional<Record> record = recordRepository.findById(id);
            if (!record.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_RECORD_NOT_FOUND);
            }
            Optional<User> user = userRepository.findById(data.getUser().getId());
            if (!user.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.ERROR_NOT_EXISTS_USER);
            }
            Optional<Project> project = projectRepository.findById(data.getProject().getId());
            if (!project.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.ERROR_NOT_EXISTS_PROJECT);
            }
            record.get().setDate(data.getDate());
            record.get().setHours(data.getHours());
            record.get().setDescription(data.getDescription());
            record.get().setUser(user.get());
            record.get().setProject(project.get());
            return recordRepository.save(record.get());
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }
}
