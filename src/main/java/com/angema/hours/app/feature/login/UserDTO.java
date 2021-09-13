package com.angema.hours.app.feature.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private String mail;
    private String name;
    private String surname;
    private String phone;
    private List<String> rol;
}
