package ci.trabrouss.saas.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "company")
public class Tenant extends AbstractEntity {

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "code", nullable = false, unique = true)
  private String code;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private TenantStatusEnum status = TenantStatusEnum.PENDING;

  //Info initial de l'admin
  @Column(name = "admin_full_name", nullable = false)
  private String adminFullName;

  @Column(name = "admin_email", nullable = false, unique = true)
  private String adminEmail;

  @Column(name = "admin_username", nullable = false, unique = true)
  private String adminUsername;

  @Column(name = "admin_password", nullable = false)
  private String adminPassword;

}
