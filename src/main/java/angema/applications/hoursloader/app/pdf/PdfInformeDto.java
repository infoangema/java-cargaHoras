package angema.applications.hoursloader.app.pdf;

import angema.applications.hoursloader.app.project.ProjectDto;
import angema.applications.hoursloader.app.record.RecordDto;
import angema.applications.hoursloader.app.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PdfInformeDto {
    public UserDto user;
    public ProjectDto project;
    public List<RecordDto> records;
    public String hours;
    public String date;
    public String description;
}
