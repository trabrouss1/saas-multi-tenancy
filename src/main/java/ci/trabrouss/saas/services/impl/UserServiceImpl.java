package ci.trabrouss.saas.services.impl;

import ci.trabrouss.saas.common.PageResponse;
import ci.trabrouss.saas.config.TenantContext;
import ci.trabrouss.saas.entites.Tenant;
import ci.trabrouss.saas.entites.User;
import ci.trabrouss.saas.entites.UserRoleEnum;
import ci.trabrouss.saas.exceptions.DuplicateResourceException;
import ci.trabrouss.saas.exceptions.InvalidRequestException;
import ci.trabrouss.saas.mappers.UserMapper;
import ci.trabrouss.saas.repositories.TenantRepository;
import ci.trabrouss.saas.repositories.UserRepository;
import ci.trabrouss.saas.requests.UserRequestDTO;
import ci.trabrouss.saas.responses.UserResponseDTO;
import ci.trabrouss.saas.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final TenantRepository tenantRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void createUser(final UserRequestDTO request) {
    final String tenantId = TenantContext.getCurrentTenant();
    log.info("Creating user for tenant: {}", tenantId);

    // validate if username exists
    if (this.userRepository.existsByUsername(request.username())) {
      throw new DuplicateResourceException("Username already exists");
    }

    // check if email exists
    if (this.userRepository.existsByEmail(request.email())) {
      throw new DuplicateResourceException("Email already exists");
    }

    // validate role (cannot be PLATFORM_ADMIN)
    if (request.role() == UserRoleEnum.ROLE_PLATFORM_ADMIN) {
      throw new InvalidRequestException("Role is required");
    }

    final User user = this.userMapper.toEntity(request);
    user.setTenant(Tenant.builder().id(tenantId).build());

    this.userRepository.save(user);

    log.info("User created successfully");
  }

  @Override
  public void updateUser(String userId, UserRequestDTO requestDTO) {
    final String tenantId = TenantContext.getCurrentTenant();
    log.info("Updating user for tenant: {}", tenantId);

    final User user = this.userRepository.findByIdAndNotDeleted(userId)
      .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    // check if user belongs to the tenant
    if (!user.getTenant().getId().equals(tenantId)) {
      throw new InvalidRequestException("User does not belong to the tenant");
    }

    // check if username is being changed and if it is already taken
    if (!user.getUsername().equals(requestDTO.username()) && this.userRepository.existsByUsername(requestDTO.username())) {
      throw new DuplicateResourceException("Username already exists");
    }

    // check if email is being changed and if it is already taken
    if (!user.getEmail().equals(requestDTO.email()) && this.userRepository.existsByEmail(requestDTO.email())) {
      throw new DuplicateResourceException("Email already exists");
    }

    // validate role (cannot be PLATFORM_ADMIN)
    if (requestDTO.role() == UserRoleEnum.ROLE_PLATFORM_ADMIN) {
      throw new InvalidRequestException("Role is required");
    }

    // update user details
    user.setUsername(requestDTO.username());
    user.setEmail(requestDTO.email());
    user.setRole(requestDTO.role());
    user.setFirstName(requestDTO.firstName());
    user.setLastName(requestDTO.lastName());
    this.userRepository.save(user);
    log.info("User updated successfully");
  }

  @Override
  public void deleteUser(String userId) {
    final String tenantId = TenantContext.getCurrentTenant();
    log.info("Deleting user for tenant: {}", tenantId);

    final User user = this.userRepository.findByIdAndNotDeleted(userId)
      .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    // check if user belongs to the tenant
    if (!user.getTenant().getId().equals(tenantId)) {
      throw new InvalidRequestException("User does not belong to the tenant");
    }

    // soft delete user
    user.setDeleted(true);
    this.userRepository.save(user);
    log.info("User deleted successfully");
  }

  @Override
  public UserResponseDTO getUserById(String userId) {

    final String tenantId = TenantContext.getCurrentTenant();
    final User user = this.userRepository.findByIdAndNotDeleted(userId)
      .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    // check if user belongs to the tenant
    if (!user.getTenant().getId().equals(tenantId)) {
      throw new InvalidRequestException("User does not belong to the tenant");
    }
    return this.userMapper.toResponse(user);
  }

  @Override
  public PageResponse<UserResponseDTO> getAllUsers(final int page, final int size) {
    final String tenantId = TenantContext.getCurrentTenant();
    final PageRequest pageRequest = PageRequest.of(page, size);
    final Page<User> userPage = this.userRepository.findAllByTenantId(tenantId, pageRequest);
    final Page<UserResponseDTO> userResponses = userPage.map(this.userMapper::toResponse);
    return PageResponse.of(userResponses);
  }

  @Override
  public void enableUser(final String userId) {
    final String tenantId = TenantContext.getCurrentTenant();
    final User user = this.userRepository.findByIdAndNotDeleted(userId)
      .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    // check if user belongs to the tenant
    if (!user.getTenant().getId().equals(tenantId)) {
      throw new InvalidRequestException("User does not belong to the tenant");
    }

    user.setEnabled(true);
    this.userRepository.save(user);
    log.info("User enabled successfully");
  }

  @Override
  public void disableUser(final String userId) {
    final String tenantId = TenantContext.getCurrentTenant();
    final User user = this.userRepository.findByIdAndNotDeleted(userId)
      .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    // check if user belongs to the tenant
    if (!user.getTenant().getId().equals(tenantId)) {
      throw new InvalidRequestException("User does not belong to the tenant");
    }

    user.setEnabled(false);
    this.userRepository.save(user);
    log.info("User disabled successfully");
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur trouvé avec " + username));
  }

}
