package ci.trabrouss.saas.services.impl;

import ci.trabrouss.saas.common.PageResponse;
import ci.trabrouss.saas.entites.Category;
import ci.trabrouss.saas.entites.Product;
import ci.trabrouss.saas.mappers.ProductMapper;
import ci.trabrouss.saas.repositories.CategoryRepository;
import ci.trabrouss.saas.repositories.ProductRepository;
import ci.trabrouss.saas.requests.ProductRequest;
import ci.trabrouss.saas.responses.ProductResponse;
import ci.trabrouss.saas.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;
  private final CategoryRepository categoryRepository;

  @Override
  public void create(ProductRequest request) {

    checkIfProductAlreadyExisteByReference(request.getReference());
    checkIfCategoryExisteById(request.getCategoryId());

    Product product = productMapper.toEntity(request);
    productRepository.save(product);
  }

  @Override
  public void update(String id, ProductRequest request) {

    final Optional<Product> productExists = productRepository.findById(id);
    if (productExists.isEmpty()){
      log.debug("Product does not exist");
      throw new EntityNotFoundException("Product does not exist");
    }

    checkIfProductAlreadyExisteByReference(request.getReference());
    checkIfCategoryExisteById(request.getCategoryId());

    Product product = productMapper.toEntity(request);
    product.setId(id);
    productRepository.save(product);

  }

  @Override
  public PageResponse<ProductResponse> findAll(int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<Product> products = productRepository.findAll(pageRequest);
    Page<ProductResponse> productResponses = products.map(productMapper::toResponse);
    return PageResponse.of(productResponses);
  }

  @Override
  public ProductResponse findById(String id) {
    return productRepository.findById(id)
      .map(productMapper::toResponse)
      .orElseThrow(() -> new EntityNotFoundException("Product does not existe"));
  }

  @Override
  public void delete(String id) {
    Product product = productRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Product does not existe"));

    productRepository.delete(product);
  }

  private void checkIfProductAlreadyExisteByReference(final String reference){
    final Optional<Product> productOptional = productRepository.findByReferenceIgnoreCase(reference);
    if (productOptional.isPresent()){
      log.debug("Product already exists");
      throw new RuntimeException("Product already exists");
    }
  }

  private void checkIfCategoryExisteById(final String categoryId){
    final Optional<Category> category = categoryRepository.findById(categoryId);
    if (category.isEmpty()){
      log.debug("Category does not exist");
      throw new EntityNotFoundException("Category does not exist");
    }
  }

}
