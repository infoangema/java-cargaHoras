package angema.applications.hoursloader.app.user;

import angema.applications.hoursloader.app.user.dtos.RoleDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class UserDto {

    public Integer id;
    public String userName;
    public String name;
    public String lastName;
    public String email;
    public String password;
    public String phone;
    public boolean active = true;
    public List<RoleDto> roles = new ArrayList<>();

}
