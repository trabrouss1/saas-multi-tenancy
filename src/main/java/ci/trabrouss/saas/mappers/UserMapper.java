package ci.trabrouss.saas.mappers;


import ci.trabrouss.saas.entites.User;
import ci.trabrouss.saas.requests.UserRequestDTO;
import ci.trabrouss.saas.responses.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
  public User toEntity(final UserRequestDTO request) {
    return User.builder()
      .username(request.username())
      .email(request.email())
      .password(request.password())
      .role(request.role())
      .firstName(request.firstName())
      .lastName(request.lastName())
      .build();
  }

  public UserResponseDTO toResponse(final User user) {
    return UserResponseDTO.builder()
      .id(user.getId())
      .username(user.getUsername())
      .email(user.getEmail())
      .password(user.getPassword())
      .role(user.getRole())
      .firstName(user.getFirstName())
      .lastName(user.getLastName())
      .build();
  }
}