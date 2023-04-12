package angema.applications.hoursloader.app.company;

import angema.applications.hoursloader.core.Constant;
import angema.applications.hoursloader.core.Messages;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class CompanyDto implements Serializable {

    private Long id;
    private String name;
    private String description;
    private String cuit;
    private String direction;

}
