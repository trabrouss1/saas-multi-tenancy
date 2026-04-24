package ci.trabrouss.saas.requests;

import ci.trabrouss.saas.entites.TypeStockMvtEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockMvtRequest {

  @NotBlank(message = "L'ID du product est obligatoire")
  private String productId;

  @NotBlank(message = "Le type est obligatoire")
  private TypeStockMvtEnum type;

  @Positive(message = "La quantité doit être positif")
  private Integer quantity;

  @NotNull(message = "La date est obligatoire")
  @PastOrPresent(message = "Date of mvt should be in the past or present") // Message indiquant que je date ne peut pas être hier... Min c'est la date du jour et max la date a venir
  private LocalDate date;

  private String comment;
}
