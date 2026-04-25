package ci.trabrouss.saas.auth.service;

import ci.trabrouss.saas.auth.dto.LoginRequestDTO;
import ci.trabrouss.saas.auth.dto.LoginResponseDTO;
import ci.trabrouss.saas.entites.User;
import ci.trabrouss.saas.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenService jwtTokenService;

  @Override
  public LoginResponseDTO login(LoginRequestDTO requestDTO) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        requestDTO.username(),
        requestDTO.password()
      )
    );

    final User user = (User) authentication.getPrincipal();
    final String token = jwtTokenService.generateAccessToken(user.getTenantId(), user.getId(), user.getRole().name());
    final String tokenType = "Bearer";

    return LoginResponseDTO.builder()
      .accessToken(token)
      .tokenType(tokenType)
      .build();
  }
}
