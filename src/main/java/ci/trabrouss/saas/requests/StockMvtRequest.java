package ci.trabrouss.saas.requests;

import ci.trabrouss.saas.entites.TypeStockMvtEnum;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockMvtRequest {

  private String productId;
  private TypeStockMvtEnum type;
  private Integer quantity;
  private LocalDate date;
  private String comment;
}
