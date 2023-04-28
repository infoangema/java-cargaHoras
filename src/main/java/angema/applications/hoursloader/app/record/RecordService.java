package angema.applications.hoursloader.app.record;

import angema.applications.hoursloader.app.project.Project;
import angema.applications.hoursloader.app.project.ProjectService;
import angema.applications.hoursloader.app.user.User;
import angema.applications.hoursloader.app.user.UserService;
import angema.applications.hoursloader.core.Messages;
import angema.applications.hoursloader.core.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DateUtil dateUtil;

    public List<RecordDto> getAllRecord() {
        try {
            List<Record> records = recordRepository.findAllByOrderByIdAsc();
            List<RecordDto> recordDtos = new ArrayList<>();
            records.forEach(record -> recordDtos.add(mapRecordToDto(record)));
            return recordDtos;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public List<RecordDto> getAllRecordsByUserId(Long idUser) {
        try {
            List<Record> records = recordRepository.findByUserIdOrderByIdDesc(idUser);
            List<RecordDto> recordDtos = new ArrayList<>();
            records.forEach(record -> recordDtos.add(mapRecordToDto(record)));
            return recordDtos;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public RecordDto getRecordDtoById(final Long id) {

        Optional<Record> record = recordRepository.findById(id);
        if (record.isPresent()) {
            return mapRecordToDto(record.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_PROJECT_NOT_FOUND);
        }
    }

    public RecordDto saveRecord(RecordDto recordDto) {
        try {
            Record record = mapDtoToRecord(recordDto);
            record = recordRepository.save(record);
            return mapRecordToDto(record);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public List<String> saveAllRecord(List<RecordDto> recorList) {
        try {
            List<String> list = new ArrayList<>();
            recorList.forEach(recordDto -> {
                Record record = mapDtoToRecord(recordDto);
                try {
                    isValidRecord(recordDto, record.user.id);
                    recordRepository.save(record);
                    list.add("fecha: " + record.date + " guardada correctamente");
                } catch (Exception e) {
                    list.add(e.getMessage());
                }
            });
//            return findRecordsDtoByCurrentMonth(recorList.get(0).user.id);
            return list;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public void deleteRecord(RecordDto recordDto) {
        try {
            recordRepository.delete(mapDtoToRecord(recordDto));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_PROJECT_NOT_FOUND, e);
        }
    }

    public Record mapDtoToRecord(RecordDto recordDto) {
        Record record = new Record();

        record.id = recordDto.id;
        record.date = recordDto.date;
        record.hours = String.valueOf(recordDto.hours);
        record.description = recordDto.description;

        User user = new User();
        user.id = recordDto.user.id;
        record.user = user;

        Project project = new Project();
        project.id = recordDto.project.id;
        record.project = project;

        return record;
    }

    public RecordDto mapRecordToDto(Record record) {
        RecordDto recordDto = new RecordDto();

        recordDto.id = record.id;
        recordDto.date = record.date;
        recordDto.hours = Integer.valueOf(record.hours);
        recordDto.description = record.description;

        recordDto.user = userService.mapUserToDto(record.user);
        recordDto.project = projectService.mapProjectToDto(record.project);

        return recordDto;
    }

    public List<RecordDto> getRecordDtoByUserId(Long id) {
        try {
            List<Record> recordList = recordRepository.findByUserId(id);
            List<RecordDto> recordDtolist = new ArrayList<>();
            recordList.forEach(record -> {
                RecordDto recordDto = new RecordDto();
                recordDto.id = record.id;
                recordDto.date = record.date;
                recordDto.hours = Integer.valueOf(record.hours);
                recordDto.description = record.description;
                recordDto.user = userService.mapUserToDto(record.user);
                recordDto.project = projectService.mapProjectToDto(record.project);

                recordDtolist.add(recordDto);
            });
            return recordDtolist;
        } catch (Exception e) {
            throw new RecordException("Error al intentar obtener los registros del user: ");
        }
    }

    public RecordDto getRecordDtoByUserIdAndRecorId(Long userId, Long recorId) {
        Record record = recordRepository.findByIdAndUserId(recorId, userId);
        RecordDto recordDto = new RecordDto();
        recordDto.id = record.id;
        recordDto.date = record.date;
        recordDto.hours = Integer.valueOf(record.hours);
        recordDto.description = record.description;
        recordDto.user = userService.mapUserToDto(record.user);
        recordDto.project = projectService.mapProjectToDto(record.project);
        return recordDto;
    }

    public String getTotalHours(List<RecordDto> recordList) {
        int totalHours = 0;
        for (RecordDto recordDto : recordList) {
            totalHours += recordDto.hours;
        }
        return String.valueOf(totalHours);
    }

    public List<Date> getMissingDays(Long userId) {
        return recordRepository.findMissingDatesByUserIdAndMonth(userId).orElseThrow(() -> new RecordException("Error al intentar obtener los registros del user: " + userId));
    }

    public List<RecordDto> findRecordsDtoByCurrentMonth(Long userId) {
        try {
            List<Record> records = recordRepository.findRecordsByUserIdAndCurrentMonth(userId).orElseThrow(() -> new RecordException("Error al intentar obtener los registros del user: " + userId));
            List<RecordDto> recordDtos = new ArrayList<>();
            records.forEach(record -> {
                record.date = dateUtil.getDayMonthString(record.date);
                recordDtos.add(mapRecordToDto(record));
            });
            return recordDtos;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Integer getCountRecordsByUserId(Long userId) {
        return recordRepository.countRecordsByUserIdAndMonth(userId).orElseThrow(() -> new RecordException("Error al intentar obtener los registros del user: " + userId));
    }

    public void isValidRecord(RecordDto data, Long id) {
        List<Record> record = recordRepository.findByDateAndUserId(data.date, id);
        if (record.size() > 0) {
            throw new RecordException("Ya existe un registro para la fecha: " + data.date);
        }
    }


}

