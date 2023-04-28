package angema.applications.hoursloader.core.auth;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARGA_HORAS_SEQUENCE")
@SequenceGenerator(name = "CARGA_HORAS_SEQUENCE", sequenceName = "CARGA_HORAS_SEQUENCE", allocationSize = 1)

    @Column(name = "id", nullable = false)
    public Integer id;
    public String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    public List<Auth> auths = new ArrayList<>();
}
