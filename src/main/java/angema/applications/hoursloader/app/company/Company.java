package angema.applications.hoursloader.app.company;

import angema.applications.hoursloader.core.Constant;
import angema.applications.hoursloader.core.Messages;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company implements Serializable {

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

    @Size(min = Constant.MIN_CHARACTER_CUIT, max = Constant.MAX_CHARACTER_CUIT, message = Messages.ERROR_CUIT)
    @NotBlank(message = Messages.ERROR_NULL_CUIT)
    public String cuit;

    @Size(min = Constant.MIN_CHARACTER_DIRECTION, max = Constant.MAX_CHARACTER_DIRECTION, message = Messages.ERROR_DIRECTION)
    @NotBlank(message = Messages.ERROR_NULL_DIRECTION)
    public String direction;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.EXTRA)
    @CollectionTable(name="emails_company", joinColumns=@JoinColumn(name="company_id"))
    @Column(name="email")
    public List<String> emails = new ArrayList<>();

    @Column(name="send_email", columnDefinition = "boolean default false")
    public boolean sendEmail;
}
