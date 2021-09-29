package com.angema.hours.app.feature.record;

import com.angema.hours.app.feature.project.Project;
import com.angema.hours.app.feature.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordStatistics implements Serializable {

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    private Integer hours;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_project")
    private Project project;
}
