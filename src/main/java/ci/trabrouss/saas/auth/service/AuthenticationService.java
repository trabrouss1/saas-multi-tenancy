package ci.trabrouss.saas.auth.service;


import ci.trabrouss.saas.auth.dto.LoginRequestDTO;
import ci.trabrouss.saas.auth.dto.LoginResponseDTO;

public interface AuthenticationService {
  LoginResponseDTO login(final LoginRequestDTO requestDTO);
}
