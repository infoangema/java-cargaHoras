package com.angema.hours.app.core.auth;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "auth", orphanRemoval = true)
    @OrderBy("name ASC")
    public List<AuthRoles> roles = new ArrayList<>();
}
