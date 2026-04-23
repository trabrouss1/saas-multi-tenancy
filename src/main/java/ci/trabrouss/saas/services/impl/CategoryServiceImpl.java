package ci.trabrouss.saas.services.impl;

import ci.trabrouss.saas.common.PageResponse;
import ci.trabrouss.saas.entites.Category;
import ci.trabrouss.saas.mappers.CategoryMapper;
import ci.trabrouss.saas.repositories.CategoryRepository;
import ci.trabrouss.saas.requests.CategoryRequest;
import ci.trabrouss.saas.responses.CategoryResponse;
import ci.trabrouss.saas.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Override
  public void create(CategoryRequest request) {

    checkIfCategoryAlreadyExistsByName(request.getName());

    Category category = categoryMapper.toEntity(request);
    categoryRepository.save(category);
  }

  @Override
  public void update(String id, CategoryRequest request) {
    // check if category existe
    Optional<Category> existingCategory = categoryRepository.findById(id);
    if (existingCategory.isEmpty()){
      log.debug("Category does not exist");
      throw new EntityNotFoundException("Category does not exist");
    }
    checkIfCategoryAlreadyExistsByName(request.getName());

    Category categoryToUpdate = categoryMapper.toEntity(request);
    categoryToUpdate.setId(id);
    categoryRepository.save(categoryToUpdate);

  }

  private void checkIfCategoryAlreadyExistsByName(final String categoryName) {

    Optional<Category> categoryOptional = categoryRepository
      .findByNameIgnoreCase(categoryName);

    categoryOptional.ifPresent(e -> {
      log.debug("Category already exists");
      throw new RuntimeException("Category already exists");
    });
  }

  @Override
  public PageResponse<CategoryResponse> findAll(int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<Category> categories = categoryRepository.findAll(pageRequest);
    Page<CategoryResponse> categoryResponses = categories.map(categoryMapper::toResponse);
    return PageResponse.of(categoryResponses);
  }

  @Override
  public CategoryResponse findById(String id) {
    return categoryRepository.findById(id)
      .map(categoryMapper::toResponse)
      .orElseThrow(() -> new EntityNotFoundException("Category does not existe"));
  }

  @Override
  public void delete(String id) {
    Category category = categoryRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Category does not existe"));

    categoryRepository.delete(category);
  }
}
