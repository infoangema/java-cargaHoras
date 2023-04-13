package angema.applications.hoursloader.core.auth;

import angema.applications.hoursloader.app.record.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByEmailAndPasswordAndActiveTrue(String user, String password);


    List<Auth> findAllByOrderByIdAsc();

    @Query(value= "select ar.role_id from auth_roles ar where auth_id = ?1", nativeQuery = true)
    Optional<String> findRoleById(String idRole);

    Optional<Auth> findByUserName(String nombreUsuario);
}
