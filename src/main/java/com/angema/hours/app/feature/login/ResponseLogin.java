package com.angema.hours.app.feature.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseLogin implements Serializable {

    private boolean error;
    private String message;
    private UserLoginBody body;
}
