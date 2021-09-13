package com.angema.hours.app.feature.login;

import com.angema.hours.app.core.Constant;
import com.angema.hours.app.core.Messages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {

    @Email(message = Messages.ERROR_FORMAT_MAIL)
    @NotBlank(message = Messages.ERROR_NULL_MAIL)
    @Size(min = Constant.MIN_CHARACTER_MAIL, max = Constant.MAX_CHARACTER_MAIL, message = Messages.ERROR_MAIL)
    private String mail;

    @Size(min = Constant.MIN_CHARACTER_PASSWORD, max = Constant.MAX_CHARACTER_PASSWORD, message = Messages.ERROR_PASSWORD)
    @NotBlank(message = Messages.ERROR_NULL_PASSWORD)
    private String password;
}
