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

    @Query(value = "SELECT date, SUM(hours) as hourx, projects.name as nameproject, users.name as nameuser, users.surname as surnameuser " +
            "FROM records join users on records.id_user = users.id join projects on records.id_project = projects.id " +
            "GROUP BY date, nameproject, nameuser, surnameuser", nativeQuery = true)
    List<RecordStatisticsDTO> findByRecordStatistics ();
}
