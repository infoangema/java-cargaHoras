package com.angema.hours.app.feature.login;

import com.angema.hours.app.feature.message.MessageResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private String mail;
    private String name;
    private String surname;
    private String phone;
    private String rol;
    private MessageResponse message;
}
