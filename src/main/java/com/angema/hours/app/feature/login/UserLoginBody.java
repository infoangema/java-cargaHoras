package com.angema.hours.app.feature.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginBody implements Serializable {

    private UserDTO user;
    private String token;
}
