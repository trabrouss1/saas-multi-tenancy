package ci.trabrouss.saas.config;

/**
 * TenantContext - Stocke l'identifiant du tenant courant dans un ThreadLocal.
 *
 * Chaque requête HTTP est traitee par un thread dedie.
 * Le ThreadLocal garantit que le tenant_id est isole par thread,
 * meme en cas de requête simultanee de tenants differents.
 *
 * Flux :
 *    1. TenantFilter extrait le tenant_id de la requete HTTP
 *    2. TenantFilter appelle TenantContext.setCurrentTenant (tenant_id)
 *    3. Le code metier (service, repositories) accede au tenant via TenantContext.getCurrentTenant()
 *    4. TenantFilter appelle TenantContext.clear() apres la réponse (nettoyage)
 *
 */
public class TenantContext {

  private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

  public static void setCurrentTenant(final String tenant){
    CURRENT_TENANT.set(tenant);
  }

  public static String getCurrentTenant(){
    return CURRENT_TENANT.get();
  }

  /**
   * Nettoie le tenant du thread courant.
   * IMPORTANT: doit être appelé dans un bloc finally
   * pour éviter les fuites de memoire (memory leak)
   * et les fuites de donnees entre requêtes HTTP.
   */
  public static void clear(){
    CURRENT_TENANT.remove();
  }

}
