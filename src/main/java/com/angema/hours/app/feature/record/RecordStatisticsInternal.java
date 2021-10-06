package com.angema.hours.app.feature.record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordStatisticsInternal implements Serializable {

    private String nameProject;
    private String nameUser;
    private String surnameUser;
    private Integer hours;
}
