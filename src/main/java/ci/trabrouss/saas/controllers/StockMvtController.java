package ci.trabrouss.saas.controllers;

import ci.trabrouss.saas.common.PageResponse;
import ci.trabrouss.saas.requests.StockMvtRequest;
import ci.trabrouss.saas.responses.StockMvtResponse;
import ci.trabrouss.saas.services.StockMvtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/stocks")
public class StockMvtController {

  private final StockMvtService stockMvtService;

  @PostMapping
  public ResponseEntity<Void> createStockMvt(@Valid @RequestBody StockMvtRequest request){
    this.stockMvtService.create(request);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{stockMvtId}")
  public ResponseEntity<Void> updateStockMvt(@PathVariable String stockMvtId, @Valid @RequestBody StockMvtRequest request){
    this.stockMvtService.update(stockMvtId, request);
    return ResponseEntity.accepted().build();
  }

  @GetMapping("/{stockMvtId}")
  public ResponseEntity<StockMvtResponse> findStockMvtById(@PathVariable String stockMvtId){
    return ResponseEntity.ok(this.stockMvtService.findById(stockMvtId));
  }

  @GetMapping
  public ResponseEntity<PageResponse<StockMvtResponse>> findAllStockMvt(
    @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "20") int size
  ){
    return ResponseEntity.ok(this.stockMvtService.findAll(page, size));
  }

  @DeleteMapping("/{stockMvtId}")
  public ResponseEntity<Void> deleteStockMvt(@PathVariable String stockMvtId){
    this.stockMvtService.delete(stockMvtId);
    return ResponseEntity.noContent().build();
  }
}
