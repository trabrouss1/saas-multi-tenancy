package ci.trabrouss.saas.mappers;

import ci.trabrouss.saas.entites.Category;
import ci.trabrouss.saas.entites.Product;
import ci.trabrouss.saas.requests.ProductRequest;
import ci.trabrouss.saas.responses.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

  public Product toEntity(final ProductRequest productRequest){
    return Product.builder()
        .name(productRequest.getName())
        .reference(productRequest.getReference())
        .alertThreshold(productRequest.getAlertThreshold())
        .price(productRequest.getPrice())
        .category(Category.builder().id(productRequest.getCategoryId()).build())
        .description(productRequest.getDescription())
      .build();
  }

  public ProductResponse toResponse(final Product entity){
    return ProductResponse.builder()
        .name(entity.getName())
        .description(entity.getDescription())
        .name(entity.getName())
        .reference(entity.getReference())
        .alertThreshold(entity.getAlertThreshold())
        .price(entity.getPrice())
        .categoryName(entity.getCategory().getName())
//        .availableQuantity() later
      .build();
  }

}
