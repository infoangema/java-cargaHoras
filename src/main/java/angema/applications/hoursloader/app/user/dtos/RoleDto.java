package angema.applications.hoursloader.app.user.dtos;

import angema.applications.hoursloader.core.auth.Auth;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RoleDto {


    public Integer id;
    public String description;

}
