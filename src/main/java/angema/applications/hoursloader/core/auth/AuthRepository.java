package angema.applications.hoursloader.core.auth;

import angema.applications.hoursloader.app.record.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByUserNameAndPasswordAndActiveTrue(String user, String password);




    Optional<Auth> findByUserName(String nombreUsuario);
}
