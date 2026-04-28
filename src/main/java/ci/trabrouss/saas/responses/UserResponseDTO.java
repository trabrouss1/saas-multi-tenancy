package ci.trabrouss.saas.responses;

import ci.trabrouss.saas.entites.UserRoleEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

  private String id;
  private String username;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private UserRoleEnum role;

}
