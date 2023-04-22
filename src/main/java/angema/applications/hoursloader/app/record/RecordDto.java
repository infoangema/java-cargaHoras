package angema.applications.hoursloader.app.record;

import angema.applications.hoursloader.app.project.ProjectDto;
import angema.applications.hoursloader.app.user.UserDto;
import angema.applications.hoursloader.core.Constant;
import angema.applications.hoursloader.core.Messages;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RecordDto {
    public Long id;
    public String date;
    public Integer hours;
    @Size(min = Constant.MIN_CHARACTER_DESCRIPTION, max = Constant.MAX_CHARACTER_DESCRIPTION, message = Messages.ERROR_DESCRIPTION)
    @NotBlank(message = Messages.ERROR_NULL_DESCRIPTION)
    public String description;
    public UserDto user;
    public ProjectDto project;
}
