package ci.trabrouss.saas.auth;

import ci.trabrouss.saas.auth.dto.LoginRequestDTO;
import ci.trabrouss.saas.auth.dto.LoginResponseDTO;
import ci.trabrouss.saas.auth.service.AuthenticationService;
import ci.trabrouss.saas.requests.RegisterTenantRequestDTO;
import ci.trabrouss.saas.services.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthentificationController {

  private final AuthenticationService authentificationService;
  private final TenantService tenantService;

  @PostMapping("/login")
  ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO){
    return ResponseEntity.ok(authentificationService.login(requestDTO));
  }

  @PostMapping("/register")
  ResponseEntity<Void> register(@Valid @RequestBody RegisterTenantRequestDTO requestDTO){
    this.tenantService.registerTenant(requestDTO);
    return ResponseEntity.ok().build();
  }

}
