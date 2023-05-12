package angema.applications.hoursloader.app.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByProjects_Company_Id(Long id);
    List<User> findByActiveTelegramIsTrue();

    @Modifying
    @Query("UPDATE User u SET u.activeTelegram = :active WHERE u.id = :userId")
    void setActiveTelegramStatus(@Param("userId") Long userId, @Param("active") Boolean active);


}
