package angema.applications.hoursloader.app.administration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdministrationDto {

    public Long userId;
    public String userName;
    public Integer hours;
    public Boolean useTelegram;
    public Boolean incomeFileStatus;
    public Boolean billFileStatus;
    public Long projectId;
    public String projectName;
    public Long companyId;
    public String companyName;
}
