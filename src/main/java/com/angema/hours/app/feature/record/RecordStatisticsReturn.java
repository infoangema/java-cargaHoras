package com.angema.hours.app.feature.record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordStatisticsReturn implements Serializable {

    private LocalDate date;
    private List<RecordStatisticsInternal> records = new ArrayList<>();
}
