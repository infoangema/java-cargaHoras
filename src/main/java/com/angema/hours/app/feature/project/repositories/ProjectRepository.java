package com.angema.hours.app.feature.project.repositories;

import com.angema.hours.app.feature.project.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
