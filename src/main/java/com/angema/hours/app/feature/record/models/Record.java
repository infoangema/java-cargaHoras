package com.angema.hours.app.feature.record.models;

import com.angema.hours.app.core.Constant;
import com.angema.hours.app.core.Messages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

    @NotBlank(message = Messages.ERROR_NULL_DATE)
    private String date;

    @Pattern(regexp = "[0-9]+", message = Messages.ERROR_FORMAT_HOURS)
    @Size(min = Constant.MIN_CHARACTER_HOURS, max = Constant.MAX_CHARACTER_HOURS, message = Messages.ERROR_HOURS)
    @NotBlank(message = Messages.ERROR_NULL_HOURS)
    private String hours;

    @Size(min = Constant.MIN_CHARACTER_DESCRIPTION, max = Constant.MAX_CHARACTER_DESCRIPTION, message = Messages.ERROR_DESCRIPTION)
    @NotBlank(message = Messages.ERROR_NULL_DESCRIPTION)
    private String description;

    @NotBlank(message = Messages.ERROR_NULL_USER)
    private String user;

    @NotBlank(message = Messages.ERROR_NULL_PROJECT)
    private String project;
}
