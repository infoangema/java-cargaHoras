package com.angema.hours.app.feature.user;

import com.angema.hours.app.core.Constant;
import com.angema.hours.app.core.Messages;
import com.angema.hours.app.core.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = Messages.ERROR_FORMAT_MAIL)
    @NotBlank(message = Messages.ERROR_NULL_MAIL)
    @Size(min = Constant.MIN_CHARACTER_MAIL, max = Constant.MAX_CHARACTER_MAIL, message = Messages.ERROR_MAIL)
    private String mail;

    @Size(min = Constant.MIN_CHARACTER_PASSWORD, max = Constant.MAX_CHARACTER_PASSWORD, message = Messages.ERROR_PASSWORD)
    @NotBlank(message = Messages.ERROR_NULL_PASSWORD)
    private String password;

    @Size(min = Constant.MIN_CHARACTER_NAME, max = Constant.MAX_CHARACTER_NAME, message = Messages.ERROR_NAME)
    @NotBlank(message = Messages.ERROR_NULL_NAME)
    private String name;

    @Size(min = Constant.MIN_CHARACTER_SURNAME, max = Constant.MAX_CHARACTER_SURNAME, message = Messages.ERROR_SURNAME)
    @NotBlank(message = Messages.ERROR_NULL_SURNAME)
    private String surname;

    @Size(min = Constant.MIN_CHARACTER_PHONE, max = Constant.MAX_CHARACTER_PHONE, message = Messages.ERROR_PHONE)
    @NotBlank(message = Messages.ERROR_NULL_PHONE)
    private String phone;

    private String rol;

    private boolean status;
}
