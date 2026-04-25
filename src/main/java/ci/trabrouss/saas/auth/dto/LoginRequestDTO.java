package ci.trabrouss.saas.auth.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(

  @NotBlank(message = "Le username est obligatoire")
  @Schema(
    description = "Le username",
    example = "user",
    requiredMode = Schema.RequiredMode.REQUIRED
  )
  String username,

  @NotBlank(message = "Le mdp est obligatoire")
  @Schema(
    description = "Le mdp",
    example = "mdp",
    requiredMode = Schema.RequiredMode.REQUIRED
  )
  String password
) {
}
