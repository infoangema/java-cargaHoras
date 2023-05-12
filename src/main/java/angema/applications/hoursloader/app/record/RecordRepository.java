package angema.applications.hoursloader.app.record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findAllByOrderByIdAsc();

    List<Record> findByUserIdOrderByIdDesc(Long id);

    List<Record> findByUserId(Long id);

    List<Record> findByDateAndUserId(String date, Long id);

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

    @Query(value = "SELECT DISTINCT to_char(d.date, 'DD-MM-YYYY') AS date\n" +
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
    Optional<List<String>> findMissingDatesByUserIdAndMonthAsStringFormat(Long userId);

    Record findByDateAndUser_id( String date, Long userId);

    @Query(value = "SELECT * FROM record WHERE user_id = ?1 AND date_trunc('month', to_date(date, 'DD-MM-YYYY')) = date_trunc('month', now()) ORDER BY to_date(date, 'DD-MM-YYYY') DESC", nativeQuery = true)
    Optional<List<Record>> findRecordsByUserIdAndCurrentMonth(Long userId);

    @Query(value = "SELECT SUM(CAST(hours AS INTEGER)) AS totalHours FROM record WHERE user_id = ?1 AND date_trunc('month', to_date(date, 'DD-MM-YYYY')) = date_trunc('month', now())", nativeQuery = true)
    Optional<Integer> findTotalHoursByUserIdAndCurrentMonth(Long userId);

    @Query(value = "SELECT * FROM record WHERE user_id = ?1 AND date_trunc('month', to_date(date, 'DD-MM-YYYY')) = date_trunc('month', now() - interval '1 month') ORDER BY to_date(date, 'DD-MM-YYYY') DESC", nativeQuery = true)
    Optional<List<Record>> findRecordsByUserIdAndPreviousMonth(Long userId);

    @Query(value = "SELECT COUNT(*)\n" +
            "FROM record\n" +
            "WHERE user_id = ?1\n" +
            "  AND date_trunc('month', to_date(date, 'DD-MM-YYYY')) = date_trunc('month', current_date)", nativeQuery = true)
    Optional<Integer> countRecordsByUserIdAndMonth(Long userId);

    @Query(value = "SELECT e.email FROM  company c left join emails_company e on c.id = e.company_id WHERE c.id = :id", nativeQuery = true)
    List<String> findEmailsById(Long id);

}
