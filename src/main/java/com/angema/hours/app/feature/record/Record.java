package com.angema.hours.app.feature.record;

import com.angema.hours.app.core.Constant;
import com.angema.hours.app.core.Messages;
import com.angema.hours.app.feature.project.Project;
import com.angema.hours.app.feature.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
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
    private Long id;

    @NotNull(message = Messages.ERROR_NULL_DATE)
    private String date;

    @Min(value = Constant.MIN_VALUE_HOURS, message = Messages.ERROR_HOURS)
    @Max(value = Constant.MAX_VALUE_HOURS, message = Messages.ERROR_HOURS)
    @NotNull(message = Messages.ERROR_NULL_HOURS)
    private Integer hours;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_project")
    private Project project;
}
