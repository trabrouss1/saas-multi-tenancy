package ci.trabrouss.saas.repositories;


import ci.trabrouss.saas.entites.Tenant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, String> {
  boolean existsByCode(@NotBlank(message = "Le code de la société est obligatoire") String code);

  boolean existsByEmail(@NotBlank(message = "Le nom complet du contact est obligatoire") @Email(message = "L'email du contact doit être valide") String email);
}
