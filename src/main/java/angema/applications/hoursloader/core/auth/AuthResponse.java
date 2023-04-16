package angema.applications.hoursloader.core.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponse {

    @JsonProperty("access_token")
    public String accessToken;
    @JsonProperty("token_type")
    public String tokenType;
    @JsonProperty("expires_in")
    public Integer expiresIn;
    @JsonProperty("refresh_token")
    public String refreshToken;
    @JsonProperty("issued_at")
    public String issuedAt;
    @JsonProperty("auth_id")
    public Long authId;
    @JsonProperty("auth_data")
    public AuthDto authDto;

}
