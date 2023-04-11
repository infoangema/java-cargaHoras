package com.angema.hours.app.feature.project;
import com.angema.hours.app.feature.company.Company;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDto {
    public Long id;
    public String name;
    public String description;
    public Company company;
    public boolean status;
}
