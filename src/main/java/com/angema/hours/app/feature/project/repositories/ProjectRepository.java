package com.angema.hours.app.feature.project.repositories;

import com.angema.hours.app.feature.project.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    public Optional<Project> findById(int id);
}
