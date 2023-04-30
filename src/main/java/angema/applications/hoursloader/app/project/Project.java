package angema.applications.hoursloader.app.project;

import angema.applications.hoursloader.app.user.User;
import angema.applications.hoursloader.core.Constant;
import angema.applications.hoursloader.core.Messages;
import angema.applications.hoursloader.app.company.Company;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
public class Project implements Serializable {

    @Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARGA_HORAS_SEQUENCE")
@SequenceGenerator(name = "CARGA_HORAS_SEQUENCE", sequenceName = "CARGA_HORAS_SEQUENCE", allocationSize = 1)

    public Long id;

    @Size(min = Constant.MIN_CHARACTER_NAME, max = Constant.MAX_CHARACTER_NAME, message = Messages.ERROR_NAME)
    @NotBlank(message = Messages.ERROR_NULL_NAME)
    public String name;

    @Size(min = Constant.MIN_CHARACTER_DESCRIPTION, max = Constant.MAX_CHARACTER_DESCRIPTION, message = Messages.ERROR_DESCRIPTION)
    @NotBlank(message = Messages.ERROR_NULL_DESCRIPTION)
    public String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    public Company company;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "projects")
    public List<User> users = new ArrayList<>();

    public boolean status;
}
