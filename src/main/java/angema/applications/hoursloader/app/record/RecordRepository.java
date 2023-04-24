package angema.applications.hoursloader.app.record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findAllByOrderByIdAsc();

    List<Record> findByUserIdOrderByIdDesc(Long id);

    List<Record> findByUserId(Long id);

    Record findByIdAndUserId(Long recorId, Long userId);

    @Query(value = "SELECT DISTINCT d.date AS date\n" +
            "FROM generate_series(\n" +
            " date_trunc('month', current_date),\n" +
            " date_trunc('month', current_date) + interval '1 month - 1 day',\n" +
            " interval '1 day'\n" +
            " ) d\n" +
            "WHERE d.date <= current_date\n" +
            " AND d.date >= date_trunc('month', current_date)\n" +
            " AND NOT EXISTS (\n" +
            " SELECT 1\n" +
            " FROM record\n" +
            " WHERE user_id = ?1" +
            " AND date_trunc('day', to_date(date, 'DD-MM-YYYY')) = d.date\n" +
            " )\n" +
            " AND EXTRACT(DOW FROM d.date) NOT IN (0,6)\n" +
            "ORDER BY date ASC", nativeQuery = true)
    Optional<List<Date>> findMissingDatesByUserIdAndMonth(Long userId);

    @Query(value = "SELECT * FROM record WHERE user_id = ?1 AND date_trunc('month', to_date(date, 'DD-MM-YYYY')) = date_trunc('month', now()) ORDER BY to_date(date, 'DD-MM-YYYY') ASC", nativeQuery = true)
    Optional<List<Record>> findRecordsByUserIdAndCurrentMonth(Long userId);

    @Query(value = "SELECT COUNT(*)\n" +
            "FROM record\n" +
            "WHERE user_id = ?1\n" +
            "  AND date_trunc('month', to_date(date, 'DD-MM-YYYY')) = date_trunc('month', current_date)", nativeQuery = true)
    Optional<Integer> countRecordsByUserIdAndMonth(Long userId);


//    @Query(value = "SELECT DISTINCT d.date AS date\n" +
//            "FROM generate_series(\n" +
//            "             date_trunc('month', current_date),\n" +
//            "             date_trunc('month', current_date) + interval '1 month - 1 day',\n" +
//            "             interval '1 day'\n" +
//            "         ) d\n" +
//            "WHERE d.date <= current_date\n" +
//            "  AND d.date >= date_trunc('month', current_date)\n" +
//            "  AND NOT EXISTS (\n" +
//            "        SELECT 1\n" +
//            "        FROM record\n" +
//            "        WHERE user_id = ?1" +
//            "          AND date_trunc('day', to_date(date, 'DD-MM-YYYY')) = d.date\n" +
//            "    )\n" +
//            "ORDER BY date ASC", nativeQuery = true)
//    List<Date> findMissingDatesByUserIdAndMonth(Long userId);
}

//    SELECT DISTINCT d::date AS date
//        FROM generate_series(
//        date_trunc('month', current_date),
//        date_trunc('month', current_date) + interval '1 month - 1 day',
//        interval '1 day'
//        ) d
//        WHERE NOT EXISTS (
//        SELECT 1
//        FROM record
//        WHERE user_id = 9
//        AND date_trunc('day', to_date(date, 'DD-MM-YYYY')) = d::date
//        )
//        ORDER BY date ASC;
