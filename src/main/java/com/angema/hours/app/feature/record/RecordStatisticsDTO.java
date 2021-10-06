package com.angema.hours.app.feature.record;

import java.time.LocalDate;

public interface RecordStatisticsDTO {

    LocalDate getDate();
    Integer getHourx();
    String getNameproject();
    String getNameuser();
    String getSurnameuser();
}
