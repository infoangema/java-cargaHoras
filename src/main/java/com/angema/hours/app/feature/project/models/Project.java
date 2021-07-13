package com.angema.hours.app.feature.project.models;

import com.angema.hours.app.core.Constant;
import com.angema.hours.app.core.Messages;
import com.angema.hours.app.feature.company.models.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = Constant.MIN_CHARACTER_NAME, max = Constant.MAX_CHARACTER_NAME, message = Messages.ERROR_NAME)
    @NotBlank(message = Messages.ERROR_NULL_NAME)
    private String name;

    @Size(min = Constant.MIN_CHARACTER_DESCRIPTION, max = Constant.MAX_CHARACTER_DESCRIPTION, message = Messages.ERROR_DESCRIPTION)
    @NotBlank(message = Messages.ERROR_NULL_DESCRIPTION)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;
}
