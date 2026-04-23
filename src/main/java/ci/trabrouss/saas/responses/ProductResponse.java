package ci.trabrouss.saas.responses;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

  private String categoryName;
  private String name;
  private String reference;
  private String description;
  private Integer alertThreshold; // seuil d'alerte
  private BigDecimal price;
  private int availableQuantity;

}
