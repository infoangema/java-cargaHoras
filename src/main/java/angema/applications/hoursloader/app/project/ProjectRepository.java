package angema.applications.hoursloader.app.project;

import angema.applications.hoursloader.app.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByOrderByIdAsc();

    @Query("SELECT p FROM Project p JOIN p.users u WHERE u.id = :userId")
    List<Project> findAllByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT c.id FROM project p left JOIN company c on p.company_id = c.id WHERE p.id = :id", nativeQuery = true)
    Long findCompanyByProjectId(Long id);
}
