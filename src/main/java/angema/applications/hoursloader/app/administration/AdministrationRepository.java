package angema.applications.hoursloader.app.administration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AdministrationRepository extends JpaRepository <Administracion, Long> {
    // crear metodo que verifique si esta cargada la factura del mes actual
    @Query(value = "SELECT COUNT(*) > 0 " +
            "FROM Administration " +
            "WHERE user_id = :userId " +
            "AND project_id = :projectId " +
            "AND EXTRACT(MONTH FROM TO_DATE(date, 'dd-MM-yyyy')) = EXTRACT(MONTH FROM CURRENT_DATE) " +
            "AND EXTRACT(YEAR FROM TO_DATE(date, 'dd-MM-yyyy')) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND facturas IS NOT NULL",
            nativeQuery = true)
    boolean isCurrentMonthInvoiceUploaded(@Param("userId") Long userId,
                                          @Param("projectId") Long projectId);

    // Obtener la factura del mes actual
    @Query(value = "SELECT facturas " +
            "FROM Administration " +
            "WHERE user_id = :userId " +
            "AND project_id = :projectId " +
            "AND EXTRACT(MONTH FROM TO_DATE(date, 'dd-MM-yyyy')) = EXTRACT(MONTH FROM CURRENT_DATE) " +
            "AND EXTRACT(YEAR FROM TO_DATE(date, 'dd-MM-yyyy')) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND facturas IS NOT NULL",
            nativeQuery = true)
    byte[] getCurrentMonthInvoice(@Param("userId") Long userId,
                                  @Param("projectId") Long projectId);


}
