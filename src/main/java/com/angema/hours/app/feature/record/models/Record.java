package com.angema.hours.app.feature.record.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "records")
public class Record implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private LocalDate date;

    @NotBlank
    @Positive
    private int hours;

    @NotBlank
    private String description;

    @NotBlank
    @Positive
    private int idUser;

    @NotBlank
    @Positive
    private int idProject;
}
