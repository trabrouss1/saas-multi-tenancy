package ci.trabrouss.saas.repositories;

import ci.trabrouss.saas.entites.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
  Optional<Category> findByNameIgnoreCase(String name);
}
