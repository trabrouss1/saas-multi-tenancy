package ci.trabrouss.saas.requests;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

  private String categoryId;
  private String name;
  private String reference;
  private String description;
  private Integer alertThreshold; // seuil d'alerte
  private BigDecimal price;

}
