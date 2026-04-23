package ci.trabrouss.saas.controllers;

import ci.trabrouss.saas.common.PageResponse;
import ci.trabrouss.saas.requests.CategoryRequest;
import ci.trabrouss.saas.responses.CategoryResponse;
import ci.trabrouss.saas.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/categories")
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping
  public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryRequest request){
    this.categoryService.create(request);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{categoryId}")
  public ResponseEntity<Void> updateCategory(@PathVariable String categoryId, @Valid @RequestBody CategoryRequest request){
    this.categoryService.update(categoryId, request);
    return ResponseEntity.accepted().build();
  }

  @GetMapping("/{categoryId}")
  public ResponseEntity<CategoryResponse> findCategoryById(@PathVariable String categoryId){
    return ResponseEntity.ok(this.categoryService.findById(categoryId));
  }

  @GetMapping
  public ResponseEntity<PageResponse<CategoryResponse>> findAllCategory(
    @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "20") int size
  ){
    return ResponseEntity.ok(this.categoryService.findAll(page, size));
  }

  @DeleteMapping("/{categoryId}")
  public ResponseEntity<Void> deleteCategory(@PathVariable String categoryId){
    this.categoryService.delete(categoryId);
    return ResponseEntity.noContent().build();
  }
}
