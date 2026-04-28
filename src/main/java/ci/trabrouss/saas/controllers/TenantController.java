package ci.trabrouss.saas.controllers;

import ci.trabrouss.saas.common.PageResponse;
import ci.trabrouss.saas.responses.TenantResponseDTO;
import ci.trabrouss.saas.services.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/organisations")
public class TenantController {

  private final TenantService tenantService;

  @PostMapping("/approve/{tenantId}")
  public ResponseEntity<Void> approveTenant(@PathVariable final String tenantId) {
    this.tenantService.approveTenant(tenantId);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/activate/{tenantId}")
  public ResponseEntity<Void> activateTenant(@PathVariable final String tenantId) {
    this.tenantService.activateTenant(tenantId);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/desactive/{tenantId}")
  public ResponseEntity<Void> desactiveTenant(@PathVariable final String tenantId) {
    this.tenantService.desactiveTenant(tenantId);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/suspend/{tenantId}")
  public ResponseEntity<Void> suspendTenant(@PathVariable final String tenantId) {
    this.tenantService.suspendTenant(tenantId);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<PageResponse<TenantResponseDTO>> findAllTenant(
    @RequestParam(defaultValue = "0", name = "page") int page,
    @RequestParam(defaultValue = "10", name = "size") int size) {
    return ResponseEntity.ok(this.tenantService.findAll(page, size));
  }

}
