package angema.applications.hoursloader.app.company;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
public class CompanyDto implements Serializable {

    public Long id;
    public String name;
    public String description;
    public String cuit;
    public String direction;

}
