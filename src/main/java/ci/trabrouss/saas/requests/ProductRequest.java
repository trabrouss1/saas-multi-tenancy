package ci.trabrouss.saas.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

  @NotBlank(message = "L'ID de la catégorie est obligatoire")
  private String categoryId;

  @NotBlank(message = "Le nom du produit est obligatoire")
  @Size(min = 3, max = 255, message = "Le nom du produit doit compris entre 3 et 255 caractères")
  private String name;


  @NotBlank(message = "La reference du produit est obligatoire")
  @Size(min = 3, max = 255, message = "La reference du produit doit compris entre 3 et 255 caractères")
  private String reference;

  private String description;

  @Positive(message = "Le seuil d'alerte du produit doit être positif")
  private Integer alertThreshold; // seuil d'alerte

  @Positive(message = "Le montant doit être supérieur a 0")
  private BigDecimal price;

}
