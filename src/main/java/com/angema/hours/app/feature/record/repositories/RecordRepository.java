package com.angema.hours.app.feature.record.repositories;

import com.angema.hours.app.feature.record.models.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {

    public Optional<Record> findById(int id);
}
