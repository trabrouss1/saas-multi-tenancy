package ci.trabrouss.saas.requests;

import ci.trabrouss.saas.entites.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
    @NotBlank(message = "Username should not be empty")
   String username,
    @NotBlank(message = "Email should not be empty")
   String email,
  @NotBlank(message = "Password should not be empty")
  @Size(min = 8, message = "Password should be at least 8 characters long")
   String password,
  @NotBlank(message = "First name should not be empty")
   String firstName,
  @NotBlank(message = "Last name should not be empty")
   String lastName,
  @NotNull(message = "Role should not be empty")
  UserRoleEnum role
) {}
