package angema.applications.hoursloader.app.user;

import angema.applications.hoursloader.app.project.Project;
import angema.applications.hoursloader.core.Constant;
import angema.applications.hoursloader.core.Messages;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARGA_HORAS_SEQUENCE")
@SequenceGenerator(name = "CARGA_HORAS_SEQUENCE", sequenceName = "CARGA_HORAS_SEQUENCE", allocationSize = 1)

    public Long id;

    public String name;

    public String lastName;

    public String email;

    @Size(min = Constant.MIN_CHARACTER_PHONE, max = Constant.MAX_CHARACTER_PHONE, message = Messages.ERROR_PHONE)
    @NotBlank(message = Messages.ERROR_NULL_PHONE)
    public String phone;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_projects",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "projects_id"))
    public List<Project> projects = new ArrayList<>();

    public Boolean activeTelegram = true;
    public String telegramUserId;

}
