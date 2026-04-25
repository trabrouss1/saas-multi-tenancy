package ci.trabrouss.saas.auth.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
  private String accessToken;
  private String tokenType;
}
