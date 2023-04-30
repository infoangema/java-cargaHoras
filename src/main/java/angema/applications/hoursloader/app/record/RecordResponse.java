package angema.applications.hoursloader.app.record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordResponse {
    public String status = "OK";
    public String message;

    public RecordResponse(String message) {
        this.message = message;
    }
}
