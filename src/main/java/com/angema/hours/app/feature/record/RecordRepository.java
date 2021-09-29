package com.angema.hours.app.feature.record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query(value = "select c from Record c where (:idUser is null or c.user.id = :idUser) and (:idProject is null or c.project.id = :idProject)")
    List<Record> findByListUser (@Param("idUser") Long idUser, @Param("idProject") Long idProject);

    @Query(value = "SELECT date, SUM(hours), id_project, id_user FROM records GROUP BY date, id_project, id_user", nativeQuery = true)
    List<RecordStatistics> findByRecordStatistics ();
}
