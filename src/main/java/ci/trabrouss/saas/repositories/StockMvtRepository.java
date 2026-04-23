package ci.trabrouss.saas.repositories;

import ci.trabrouss.saas.entites.StockMvt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMvtRepository extends JpaRepository<StockMvt, String> {
}
