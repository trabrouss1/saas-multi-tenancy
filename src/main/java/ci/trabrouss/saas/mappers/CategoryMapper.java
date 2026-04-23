package ci.trabrouss.saas.mappers;

import ci.trabrouss.saas.entites.Category;
import ci.trabrouss.saas.requests.CategoryRequest;
import ci.trabrouss.saas.responses.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

  public Category toEntity(final CategoryRequest categoryRequest){
    return Category.builder()
        .name(categoryRequest.getName())
        .description(categoryRequest.getDescription())
      .build();
  }

  public CategoryResponse toResponse(final Category entity){
    int nbProduct = 0; // entity.getProducts() == null ? 0 : entity.getProducts().size();
    return CategoryResponse.builder()
        .name(entity.getName())
        .description(entity.getDescription())
        .nbProducts(nbProduct)
      .build();
  }

}
