package ci.trabrouss.saas.mappers;

import ci.trabrouss.saas.entites.Tenant;
import ci.trabrouss.saas.requests.RegisterTenantRequestDTO;
import ci.trabrouss.saas.responses.TenantResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TenantMapper {

  public Tenant toEntity(final RegisterTenantRequestDTO requestDTO) {
    return Tenant.builder()
        .name(requestDTO.name())
        .code(requestDTO.code())
        .email(requestDTO.email())
        .adminFullName(requestDTO.adminFullName())
        .adminEmail(requestDTO.adminEmail())
        .adminUsername(requestDTO.adminUsername())
        .createdAt(LocalDateTime.now())
      .build();
  }


  public TenantResponseDTO toResponse(final Tenant entity) {
    return new TenantResponseDTO(
        entity.getId(),
        entity.getName(),
        entity.getCode(),
        entity.getEmail(),
        entity.getAdminFullName(),
        entity.getAdminEmail(),
        entity.getAdminUsername(),
        entity.getAdminPassword(),
        entity.getCreatedAt(),
        entity.getStatus()
    );
  }

}
