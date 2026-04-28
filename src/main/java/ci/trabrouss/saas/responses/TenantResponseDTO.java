package ci.trabrouss.saas.responses;

import ci.trabrouss.saas.entites.TenantStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TenantResponseDTO {

  private String id;
  private String name;
  private String code;
  private String email;
  private String adminFullName;
  private String adminEmail;
  private String adminUsername;
  private String adminPassword;
  private LocalDateTime createdAt;
  private TenantStatusEnum status;

}
