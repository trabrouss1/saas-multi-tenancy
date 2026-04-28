package ci.trabrouss.saas.repositories;


import ci.trabrouss.saas.entites.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByUsername(String username);
  boolean existsByUsername(String username);

  boolean existsByEmail(@NotBlank(message = "Email should not be empty") String email);

  @Query("SELECT u FROM User u WHERE u.id = :id AND u.deleted = false")
  Optional<User> findByIdAndNotDeleted(String id);

  @Query("SELECT u FROM User u WHERE u.tenant.id = :tenantId AND u.deleted = false")
  Page<User> findAllByTenantId(String tenantId, Pageable pageable);

}
