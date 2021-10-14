package com.angema.hours.app.feature.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "select * from message where id_user = :idUser", nativeQuery = true)
    Optional<Message> findByUserMessage(@Param("idUser") Long idUser);
}
