package com.angema.hours.app.feature.company;

import com.angema.hours.app.core.Constant;
import com.angema.hours.app.core.Messages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "companies")
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = Constant.MIN_CHARACTER_NAME, max = Constant.MAX_CHARACTER_NAME, message = Messages.ERROR_NAME)
    @NotBlank(message = Messages.ERROR_NULL_NAME)
    private String name;

    @NotNull(message = Messages.ERROR_NULL_DESCRIPTION)
    private String description;

    @Size(min = Constant.MIN_CHARACTER_CUIT, max = Constant.MAX_CHARACTER_CUIT, message = Messages.ERROR_CUIT)
    @NotBlank(message = Messages.ERROR_NULL_CUIT)
    private String cuit;

    @Size(min = Constant.MIN_CHARACTER_DIRECTION, max = Constant.MAX_CHARACTER_DIRECTION, message = Messages.ERROR_DIRECTION)
    @NotBlank(message = Messages.ERROR_NULL_DIRECTION)
    private String direction;
}
