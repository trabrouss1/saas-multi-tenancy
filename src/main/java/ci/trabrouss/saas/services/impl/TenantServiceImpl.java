package ci.trabrouss.saas.services.impl;

import ci.trabrouss.saas.common.PageResponse;
import ci.trabrouss.saas.entites.Tenant;
import ci.trabrouss.saas.entites.TenantStatusEnum;
import ci.trabrouss.saas.entites.User;
import ci.trabrouss.saas.entites.UserRoleEnum;
import ci.trabrouss.saas.exceptions.DuplicateResourceException;
import ci.trabrouss.saas.exceptions.InvalidRequestException;
import ci.trabrouss.saas.mappers.TenantMapper;
import ci.trabrouss.saas.repositories.TenantRepository;
import ci.trabrouss.saas.repositories.UserRepository;
import ci.trabrouss.saas.requests.RegisterTenantRequestDTO;
import ci.trabrouss.saas.responses.TenantResponseDTO;
import ci.trabrouss.saas.services.ProvisioningService;
import ci.trabrouss.saas.services.TenantService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantServiceImpl implements TenantService {

  private final TenantRepository tenantRepository;
  private final TenantMapper tenantMapper;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final ProvisioningService provisioningService;


  @Override
  @Transactional
  public void registerTenant(RegisterTenantRequestDTO requestDTO) {
    // check if tenant with the same code or email already exists
    if (tenantRepository.existsByCode(requestDTO.code())) {
      log.warn("Tenant with code {} already exists", requestDTO.code());
      throw new DuplicateResourceException("Tenant with code " + requestDTO.code() + " already exists");
    }
    if (tenantRepository.existsByEmail(requestDTO.email())) {
      log.warn("Tenant with email {} already exists", requestDTO.email());
      throw new DuplicateResourceException("Tenant with email " + requestDTO.email() + " already exists");
    }

    // create tenant entity from request DTO
    Tenant tenant = tenantMapper.toEntity(requestDTO);
    tenant.setAdminPassword(this.passwordEncoder.encode(requestDTO.adminPassword()));
    tenant.setStatus(TenantStatusEnum.PENDING);

    this.tenantRepository.save(tenant);
  }

  @Override
  public void approveTenant(String tenantId) {
    // check if tenant existe

    final Tenant tenant = tenantRepository.findById(tenantId)
        .orElseThrow(() -> new EntityNotFoundException("Tenant with id " + tenantId + " not found"));

    // approve tenant
    tenant.setStatus(TenantStatusEnum.ACTIVE);
    this.tenantRepository.save(tenant);

    try {
      //provision the schema for the tenant

      this.provisioningService.provisionTenant(tenant);

      // create initial admin user
      createInitialAdminUser(tenant);
    } catch (final Exception e) {
      rollbackTenantStatus(tenant);
    }

  }

  private void rollbackTenantStatus(final Tenant tenant) {
    tenant.setStatus(TenantStatusEnum.PENDING);
    this.tenantRepository.save(tenant);
  }

  private void createInitialAdminUser(final Tenant tenant) {
    if(userRepository.existsByUsername(tenant.getAdminUsername())){
      log.warn("User with username {} already exists", tenant.getAdminUsername());
      throw new DuplicateResourceException("User with username " + tenant.getAdminUsername() + " already exists");
    }

    final User adminUser = User.builder()
        .username(tenant.getAdminUsername())
        .email(tenant.getAdminEmail())
        .password(this.passwordEncoder.encode(tenant.getAdminPassword()))
        .firstName(extractFirstName(tenant.getAdminFullName()))
        .lastName(extractLastName(tenant.getAdminFullName()))
        .role(UserRoleEnum.ROLE_COMPANY_ADMIN)
        .enabled(true)
        .tenant(tenant)
        .build();

    this.userRepository.save(adminUser);
    log.info("Admin user with username {} created for tenant {}", adminUser.getUsername(), tenant.getName());
  }

  private String extractFirstName(final String fullName) {
      return fullName.split(" ")[0];
  }

  private String extractLastName(final String fullName) {
     return fullName.split(" ").length > 1 ? fullName.split(" ")[1] : fullName;
  }

  @Override
  public void activateTenant(String tenantId) {
    final Tenant tenant = tenantRepository.findById(tenantId)
        .orElseThrow(() -> new EntityNotFoundException("Tenant with id " + tenantId + " not found"));

    if (tenant.getStatus() != TenantStatusEnum.PENDING) {
      log.warn("Tenant with id {} is not in PENDING status", tenantId);
      throw new InvalidRequestException("Tenant with id " + tenantId + " is not in PENDING status");
    }

    tenant.setStatus(TenantStatusEnum.ACTIVE);
  }

  @Override
  public void desactiveTenant(String tenantId) {
    final Tenant tenant = tenantRepository.findById(tenantId)
          .orElseThrow(() -> new EntityNotFoundException("Tenant with id " + tenantId + " not found"));

      if (tenant.getStatus() != TenantStatusEnum.ACTIVE) {
        log.warn("Tenant with id {} is not in ACTIVE status", tenantId);
        throw new InvalidRequestException("Tenant with id " + tenantId + " is not in ACTIVE status");
      }

      tenant.setStatus(TenantStatusEnum.INACTIVE);
  }

  @Override
  public void suspendTenant(String tenantId) {
    final Tenant tenant = tenantRepository.findById(tenantId)
          .orElseThrow(() -> new EntityNotFoundException("Tenant with id " + tenantId + " not found"));

      if (tenant.getStatus() != TenantStatusEnum.ACTIVE) {
        log.warn("Tenant with id {} is not in ACTIVE status", tenantId);
        throw new InvalidRequestException("Tenant with id " + tenantId + " is not in ACTIVE status");
      }

      tenant.setStatus(TenantStatusEnum.SUSPENDED);

  }

  @Override
  public PageResponse<TenantResponseDTO> findAll(int page, int size) {

    final PageRequest pageRequest = PageRequest.of(page, size);
    final Page<Tenant> tenantPage = this.tenantRepository.findAll(pageRequest);
    final Page<TenantResponseDTO> tenantResponsePage = tenantPage.map(this.tenantMapper::toResponse);

    return PageResponse.of(tenantResponsePage);
  }
}
