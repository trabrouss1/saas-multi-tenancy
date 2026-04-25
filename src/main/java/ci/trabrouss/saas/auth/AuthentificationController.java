package ci.trabrouss.saas.auth;

import ci.trabrouss.saas.auth.dto.LoginRequestDTO;
import ci.trabrouss.saas.auth.dto.LoginResponseDTO;
import ci.trabrouss.saas.auth.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthentificationController {

  private final AuthenticationService authentificationService;

  @PostMapping("/login")
  ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO){
    return ResponseEntity.ok(authentificationService.login(requestDTO));
  }

}
