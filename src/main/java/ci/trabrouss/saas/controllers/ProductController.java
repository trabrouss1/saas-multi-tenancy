package ci.trabrouss.saas.controllers;

import ci.trabrouss.saas.common.PageResponse;
import ci.trabrouss.saas.requests.ProductRequest;
import ci.trabrouss.saas.responses.ProductResponse;
import ci.trabrouss.saas.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductRequest request){
    this.productService.create(request);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{productId}")
  public ResponseEntity<Void> updateProduct(@PathVariable String productId, @Valid @RequestBody ProductRequest request){
    this.productService.update(productId, request);
    return ResponseEntity.accepted().build();
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ProductResponse> findProductById(@PathVariable String productId){
    return ResponseEntity.ok(this.productService.findById(productId));
  }

  @GetMapping
  public ResponseEntity<PageResponse<ProductResponse>> findAllProduct(
    @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "20") int size
  ){
    return ResponseEntity.ok(this.productService.findAll(page, size));
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<Void> deleteProduct(@PathVariable String productId){
    this.productService.delete(productId);
    return ResponseEntity.noContent().build();
  }
}
