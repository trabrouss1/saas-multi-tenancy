package ci.trabrouss.saas.responses;

import ci.trabrouss.saas.entites.TypeStockMvtEnum;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockMvtResponse {

  private TypeStockMvtEnum type;
  private Integer quantity;
  private LocalDate date;
  private String comment;

}
