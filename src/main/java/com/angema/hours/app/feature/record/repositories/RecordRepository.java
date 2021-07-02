package com.angema.hours.app.feature.record.repositories;

import com.angema.hours.app.feature.record.models.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

}
