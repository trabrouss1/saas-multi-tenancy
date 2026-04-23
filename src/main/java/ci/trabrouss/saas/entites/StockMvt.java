package ci.trabrouss.saas.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

import static jakarta.persistence.EnumType.STRING;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "stock_mvts")
public class StockMvt extends AbstractEntity {

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @Column(name = "type", nullable = false)
  @Enumerated(STRING)
  private TypeStockMvtEnum type;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  @Column(name = "date", nullable = false)
  private LocalDate date;

  @Column(name = "comment", columnDefinition = "TEXT")
  private String comment;

}
