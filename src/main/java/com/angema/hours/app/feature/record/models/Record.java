package com.angema.hours.app.feature.record.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "records")
public class Record implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String date;

    @NotBlank
    private String hours;

    @NotBlank
    private String description;

    @NotBlank
    private String idUser;

    @NotBlank
    private String idProject;
}
