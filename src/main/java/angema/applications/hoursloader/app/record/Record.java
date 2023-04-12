package angema.applications.hoursloader.app.record;

import angema.applications.hoursloader.core.Constant;
import angema.applications.hoursloader.core.Messages;
import angema.applications.hoursloader.core.auth.Auth;
import angema.applications.hoursloader.app.project.Project;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "records")
public class Record implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = Messages.ERROR_NULL_DATE)
    private String date;

    @Pattern(regexp = "[0-9]+", message = Messages.ERROR_FORMAT_HOURS)
    @Size(min = Constant.MIN_CHARACTER_HOURS, max = Constant.MAX_CHARACTER_HOURS, message = Messages.ERROR_HOURS)
    @NotBlank(message = Messages.ERROR_NULL_HOURS)
    private String hours;

    @Size(min = Constant.MIN_CHARACTER_DESCRIPTION, max = Constant.MAX_CHARACTER_DESCRIPTION, message = Messages.ERROR_DESCRIPTION)
    @NotBlank(message = Messages.ERROR_NULL_DESCRIPTION)
    private String description;

   @ManyToOne
    @JoinColumn(name = "user_id")
    private Auth user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
