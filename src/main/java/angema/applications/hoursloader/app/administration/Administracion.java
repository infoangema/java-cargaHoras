package angema.applications.hoursloader.app.administration;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;


@Entity
@Table(
        name = "ADMINISTRATION",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "projectId", "companyId", "date"})}
)
public class Administracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Long userId;
    public Long projectId;
    public Long companyId;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    public String date;
    @Lob
    @Column(name = "facturas")
    @Type(type = "org.hibernate.type.BinaryType")
    public byte[] factura = null;

    @Lob
    @Column(name = "haberes")
    @Type(type = "org.hibernate.type.BinaryType")
    public byte[] haber = null;
}

