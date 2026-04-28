package ci.trabrouss.saas.services;


import ci.trabrouss.saas.common.PageResponse;
import ci.trabrouss.saas.requests.UserRequestDTO;
import ci.trabrouss.saas.responses.UserResponseDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  void createUser(final UserRequestDTO requestDTO);

  void updateUser(final String userId, final UserRequestDTO requestDTO);

  void deleteUser(final String userId);

  UserResponseDTO getUserById(final String userId);

  PageResponse<UserResponseDTO> getAllUsers(final int page, final int size);

  void enableUser(final String userId);

  void disableUser(final String userId);

}
