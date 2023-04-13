package angema.applications.hoursloader.app.record;

import angema.applications.hoursloader.app.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findAllByOrderByIdAsc();
}
