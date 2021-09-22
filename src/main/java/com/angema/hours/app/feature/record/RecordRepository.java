package com.angema.hours.app.feature.record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query(value = "select c from Record c where (:idUser is null or c.user.id = :idUser) and (:idProject is null or c.project.id = :idProject)")
    List<Record> findByListUser (@Param("idUser") Long idUser, @Param("idProject") Long idProject);
}

//('$1' is null or c.date between :datefrom and :dateto)
//@Param("datefrom") LocalDate datefrom, @Param("dateto") LocalDate dateto
