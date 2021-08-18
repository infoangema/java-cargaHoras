package com.angema.hours.app.feature.contact;

import com.angema.hours.app.core.Constant;
import com.angema.hours.app.core.Messages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contacts")
public class Contact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = Constant.MIN_CHARACTER_NAME, max = Constant.MAX_CHARACTER_NAME, message = Messages.ERROR_NAME)
    @NotBlank(message = Messages.ERROR_NULL_NAME)
    private String name;

    @Size(min = Constant.MIN_CHARACTER_COMPANY, max = Constant.MAX_CHARACTER_COMPANY, message = Messages.ERROR_NAME)
    @NotBlank(message = Messages.ERROR_NULL_COMPANY)
    private String company;

    @Email(message = Messages.ERROR_FORMAT_MAIL)
    @NotBlank(message = Messages.ERROR_NULL_MAIL)
    @Size(min = Constant.MIN_CHARACTER_MAIL, max = Constant.MAX_CHARACTER_MAIL, message = Messages.ERROR_MAIL)
    private String mail;

    @Size(min = Constant.MIN_CHARACTER_PHONE, max = Constant.MAX_CHARACTER_PHONE, message = Messages.ERROR_PHONE)
    @NotBlank(message = Messages.ERROR_NULL_PHONE)
    private String phone;

    @NotNull(message = Messages.ERROR_NULL_DESCRIPTION)
    private String description;
}
