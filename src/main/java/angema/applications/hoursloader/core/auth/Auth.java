package angema.applications.hoursloader.core.auth;

import angema.applications.hoursloader.app.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Auth {
    @Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARGA_HORAS_SEQUENCE")
@SequenceGenerator(name = "CARGA_HORAS_SEQUENCE", sequenceName = "CARGA_HORAS_SEQUENCE", allocationSize = 1)

    public Long id;

    public String userName;

    public String password;

    public boolean active = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "auth_roles",
            joinColumns = @JoinColumn(name = "auth_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    public List<Role> roles = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    public User user;

}
