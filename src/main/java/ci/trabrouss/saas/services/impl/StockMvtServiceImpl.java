package ci.trabrouss.saas.services.impl;

import ci.trabrouss.saas.common.PageResponse;
import ci.trabrouss.saas.entites.Product;
import ci.trabrouss.saas.entites.StockMvt;
import ci.trabrouss.saas.mappers.StockMvtMapper;
import ci.trabrouss.saas.repositories.ProductRepository;
import ci.trabrouss.saas.repositories.StockMvtRepository;
import ci.trabrouss.saas.requests.StockMvtRequest;
import ci.trabrouss.saas.responses.StockMvtResponse;
import ci.trabrouss.saas.services.StockMvtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockMvtServiceImpl implements StockMvtService {

  private final StockMvtRepository stockMvtRepository;
  private final StockMvtMapper stockMvtMapper;
  private final ProductRepository productRepository;

  @Override
  public void create(StockMvtRequest request) {
    checkIfProductAlreadyExisteById(request.getProductId());

    StockMvt stockMvt = stockMvtMapper.toEntity(request);
    stockMvtRepository.save(stockMvt);
  }

  @Override
  public void update(String id, StockMvtRequest request) {

    Optional<StockMvt> stockMvtExists = stockMvtRepository.findById(id);
    if (stockMvtExists.isEmpty()){
      log.debug("stockMvt already exists");
      throw new EntityNotFoundException("stockMvt already exists");
    }

    checkIfProductAlreadyExisteById(request.getProductId());

    StockMvt stockMvt = stockMvtMapper.toEntity(request);
    stockMvt.setId(id);
    stockMvtRepository.save(stockMvt);

  }

  @Override
  public PageResponse<StockMvtResponse> findAll(int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<StockMvt> stockMvts = stockMvtRepository.findAll(pageRequest);
    Page<StockMvtResponse> stockMvtResponses = stockMvts.map(stockMvtMapper::toResponse);
    return PageResponse.of(stockMvtResponses);
  }

  @Override
  public StockMvtResponse findById(String id) {
    return stockMvtRepository.findById(id)
      .map(stockMvtMapper::toResponse)
      .orElseThrow(() -> new EntityNotFoundException("StockMvt does not existe"));

  }

  @Override
  public void delete(String id) {
    StockMvt stockMvt = stockMvtRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("StockMvt does not existe"));

    stockMvtRepository.delete(stockMvt);
  }

  private void checkIfProductAlreadyExisteById(final String productId){
    final Optional<Product> product = productRepository.findById(productId);
    if (product.isPresent()){
      log.debug("Product already exists");
      throw new EntityNotFoundException("Product already exists");
    }
  }

}
