package angema.applications.hoursloader.core.auth;

import angema.applications.hoursloader.app.project.Project;
import angema.applications.hoursloader.core.Constant;
import angema.applications.hoursloader.core.Messages;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String userName;

    public String password;

    public boolean active = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "auth_roles",
            joinColumns = @JoinColumn(name = "auth_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    public List<Role> roles = new ArrayList<>();

}
