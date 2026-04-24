package ci.trabrouss.saas.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {

  @NotBlank(message = "Le nom de la catégorie est obligatoire")
  @Size(min = 3, max = 255, message = "Le nom de la catégorie doit compris entre 3 et 255 caractères")
  private String name;

  private String description;
}
