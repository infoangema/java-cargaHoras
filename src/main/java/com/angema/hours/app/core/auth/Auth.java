package com.angema.hours.app.core.auth;

import com.angema.hours.app.core.Constant;
import com.angema.hours.app.core.Messages;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public Long id;

    public String userName;

    public String name;

    public String lastName;

    public String email;

    public String password;

    @Size(min = Constant.MIN_CHARACTER_PHONE, max = Constant.MAX_CHARACTER_PHONE, message = Messages.ERROR_PHONE)
    @NotBlank(message = Messages.ERROR_NULL_PHONE)
    public String phone;

    public boolean active = true;

    @Transient
    public List<String> roles = new ArrayList<>();

}