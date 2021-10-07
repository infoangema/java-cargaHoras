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

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<Record> getListFilter (final LocalDate datefrom, final LocalDate dateto, Long idUser, Long idProject) {
        List<Record> listFilter = recordRepository.findByListUser(idUser, idProject);
        if (datefrom != null && listFilter.size() != 0) {
            if (dateto != null) {
                if (datefrom.compareTo(dateto) > 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.ERROR_VALIDATION_DATE);
                }
                return listFilter.stream().filter(x -> (x.getDate().compareTo(datefrom) >= 0 && (x.getDate().compareTo(dateto) <= 0)) ).collect(Collectors.toList());
            } else {
                return listFilter.stream().filter(x -> (x.getDate().compareTo(datefrom) == 0) ).collect(Collectors.toList());
            }
        }
        if (datefrom == null && dateto != null && listFilter.size() != 0) {
            return listFilter.stream().filter(x -> (x.getDate().compareTo(dateto) == 0) ).collect(Collectors.toList());
        }
        return listFilter;
    }

    public List<RecordStatisticsReturn> getStatisticsRecord () {
        try {
            List<RecordStatisticsDTO> objs = recordRepository.findByRecordStatistics();
            if (objs.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_STATISTICS);
            }
            Collections.sort(objs, (o1, o2) -> o2.getDate().compareTo(o1.getDate()));
            List<RecordStatisticsReturn> rse = new ArrayList<>();
            int i = 0;
            while(i < objs.size()) {
                RecordStatisticsReturn objExternal = new RecordStatisticsReturn();
                int month = objs.get(i).getDate().getMonthValue();
                int year = objs.get(i).getDate().getYear();
                LocalDate data = LocalDate.of(year, month, 1);
                objExternal.setDate(data);
                List<RecordStatisticsInternal> rsi = new ArrayList<>();
                while(i < objs.size() && (objs.get(i).getDate().getMonthValue() == month && objs.get(i).getDate().getYear() == year)) {
                    RecordStatisticsInternal objInternal = new RecordStatisticsInternal();
                    objInternal.setNameProject(objs.get(i).getNameproject());
                    objInternal.setNameUser(objs.get(i).getNameuser());
                    objInternal.setSurnameUser(objs.get(i).getSurnameuser());
                    objInternal.setHours(objs.get(i).getHourx());
                    rsi.add(objInternal);
                    i++;
                }
                objExternal.setRecords(rsi);
                rse.add(objExternal);
            }
            return rse;
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }
}
