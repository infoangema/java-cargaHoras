package com.angema.hours.app.feature.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse implements Serializable {

    private String url;
    private boolean status;
    private String message;
    private Date dateTo;
    private Date dateFrom;
}
