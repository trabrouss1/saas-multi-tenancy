package ci.trabrouss.saas.services;


import ci.trabrouss.saas.common.PageResponse;
import ci.trabrouss.saas.requests.RegisterTenantRequestDTO;
import ci.trabrouss.saas.responses.TenantResponseDTO;

public interface TenantService {

  void registerTenant(final RegisterTenantRequestDTO requestDTO);

  void approveTenant(final String tenantId);

  void activateTenant(final String tenantId);

  void desactiveTenant(final String tenantId);

  void suspendTenant(final String tenantId);

  PageResponse<TenantResponseDTO> findAll(final int page, final int size);

}
