package angema.applications.hoursloader.app.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository <Company, Long> {

    List<Company> findAllByOrderByIdAsc();

    List<Company> findAllBySendEmailTrue();
}
