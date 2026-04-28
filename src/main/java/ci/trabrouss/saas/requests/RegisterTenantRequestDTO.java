package ci.trabrouss.saas.requests;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterTenantRequestDTO(

  @NotBlank(message = "Le nom de la société est obligatoire")
  String name,

  @NotBlank(message = "Le code de la société est obligatoire")
  String code,

  @NotBlank(message = "Le nom complet du contact est obligatoire")
  @Email(message = "L'email du contact doit être valide")
  String email,

  @NotBlank(message = "Le nom complet de l'administrateur est obligatoire")
  String adminFullName,

  @NotBlank(message = "L'email de l'administrateur est obligatoire")
  @Email(message = "L'email du contact doit être valide")
  String adminEmail,

  @NotBlank(message = "Le nom d'utilisateur de l'administrateur est obligatoire")
  String adminUsername,

  @NotBlank(message = "Le mot de passe de l'administrateur est obligatoire")
  String adminPassword
) {
}
