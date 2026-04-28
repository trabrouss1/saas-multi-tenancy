package ci.trabrouss.saas.services;


import ci.trabrouss.saas.entites.Tenant;

public interface ProvisioningService {
  void provisionTenant(final Tenant tenant);
}
