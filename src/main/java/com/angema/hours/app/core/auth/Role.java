package com.angema.hours.app.core.auth;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public String id;

    public String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    public List<Auth> auths = new ArrayList<>();

}
