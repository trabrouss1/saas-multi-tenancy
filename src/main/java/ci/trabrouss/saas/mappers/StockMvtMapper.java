package ci.trabrouss.saas.mappers;

import ci.trabrouss.saas.entites.Product;
import ci.trabrouss.saas.entites.StockMvt;
import ci.trabrouss.saas.requests.StockMvtRequest;
import ci.trabrouss.saas.responses.StockMvtResponse;
import org.springframework.stereotype.Component;

@Component
public class StockMvtMapper {

  public StockMvt toEntity(final StockMvtRequest stockMvtRequest){

    return StockMvt.builder()
        .date(stockMvtRequest.getDate())
        .type(stockMvtRequest.getType())
        .comment(stockMvtRequest.getComment())
        .quantity(stockMvtRequest.getQuantity())
        .product(Product.builder().id(stockMvtRequest.getProductId()).build())
      .build();

  }

  public StockMvtResponse toResponse(final StockMvt entity){
    return StockMvtResponse.builder()
        .date(entity.getDate())
        .type(entity.getType())
        .comment(entity.getComment())
        .quantity(entity.getQuantity())
      .build();
  }

}
