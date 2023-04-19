package angema.applications.hoursloader.app.record;

import angema.applications.hoursloader.app.project.Project;
import angema.applications.hoursloader.app.project.ProjectDto;
import angema.applications.hoursloader.app.project.ProjectService;
import angema.applications.hoursloader.app.user.User;
import angema.applications.hoursloader.app.user.UserDto;
import angema.applications.hoursloader.app.user.UserService;
import angema.applications.hoursloader.core.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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

    public void deleteRecord(RecordDto recordDto) {
        try {
            recordRepository.delete(mapDtoToRecord(recordDto));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_PROJECT_NOT_FOUND, e);
        }
    }
    private static Record mapDtoToRecord(RecordDto recordDto) {
        Record record = new Record();

        record.id = recordDto.id;
        record.date = recordDto.date;
        record.hours = recordDto.hours;
        record.description = recordDto.description;

        User user = new User();
        user.id = recordDto.user.id;
        record.user = user;

        Project project = new Project();
        project.id = recordDto.project.id;
        record.project = project;

        return record;
    }
    private static RecordDto mapRecordToDto(Record record) {
        RecordDto recordDto = new RecordDto();

        recordDto.id = record.id;
        recordDto.date = record.date;
        recordDto.hours = record.hours;
        recordDto.description = record.description;

        UserDto user = new UserDto();
        user.id = record.user.id;
        recordDto.user = user;

        ProjectDto project = new ProjectDto();
        project.id = record.project.id;
        recordDto.project = project;

        return recordDto;
    }

    public List<RecordDto> getRecordDtoByUserId(Long id) {
        try {
            List<Record> recordList = recordRepository.findByUserId(id);
            List<RecordDto> recordDtolist = new ArrayList<>();
            recordList.forEach( record -> {
                RecordDto recordDto = new RecordDto();
                recordDto.id = record.id;
                recordDto.date = record.date;
                recordDto.hours = record.hours;
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
        recordDto.hours = record.hours;
        recordDto.description = record.description;
        recordDto.user = userService.mapUserToDto(record.user);
        recordDto.project = projectService.mapProjectToDto(record.project);
        return recordDto;
    }
}
