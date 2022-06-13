package com.angema.hours.app.core.auth;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AuthUserLoggedIn {
    public String userName;
    public String name;
    public String lastName;
    public String email;
    public String phone;
    public List<String> roles;
}
