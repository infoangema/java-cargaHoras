package angema.applications.hoursloader.core.auth;

import angema.applications.hoursloader.app.user.dtos.RoleDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {

    public Long id;

    @JsonProperty("username")
    public String userName;

    public boolean active = true;

    public List<RoleDto> roles = new ArrayList<>();

}
