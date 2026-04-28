package ci.trabrouss.saas.services.impl;

import ci.trabrouss.saas.entites.Tenant;
import ci.trabrouss.saas.exceptions.TenantProvisioningException;
import ci.trabrouss.saas.services.ProvisioningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProvisioningServiceImpl implements ProvisioningService {

  private final JdbcTemplate jdbcTemplate;
  private final DataSource dataSource;

  @Override
  public void provisionTenant(Tenant tenant) {
    final String schemaName = "tenant_"+tenant.getName().toLowerCase().replaceAll("\\s+", "_");

    try {
      log.info("Provisioning tenant {} with schema {}", tenant.getName(), schemaName);
      // 1. Create this postgres schema
      createSchema(schemaName);
      log.info("Schema {} created successfully for tenant {}", schemaName, tenant.getName());

      // 2. Run Flyway migrations for this schema
      runTenantMigrations(schemaName);
      log.info("Flyway migrations executed successfully for tenant {}", tenant.getName());

      // 3. Initialize the default data (optional)
      initializeDefaultData(schemaName, tenant);

    } catch (Exception e) {
      // log error and handle provisioning failure
      log.error("Failed to provision tenant {}: {}", tenant.getName(), e.getMessage(), e);

      // RollBack : drop schema creation
      try {
        log.info("Rolling back provisioning for tenant {} by dropping schema {}", tenant.getName(), schemaName);
         dropSchema(schemaName);
      } catch (Exception ex) {
        log.error("Failed to roll back provisioning for tenant {}: {}", tenant.getName(), ex.getMessage(), ex);
      }
      throw new TenantProvisioningException("Failed to provision tenant");
    }

  }

  private void dropSchema(String schemaName) {
    final String dropSchemaSql = String.format("DROP SCHEMA IF EXISTS %s CASCADE", schemaName);
    this.jdbcTemplate.execute(dropSchemaSql);
    log.info("Schema {} dropped successfully", schemaName);
  }

  private void initializeDefaultData(String schemaName, Tenant tenant) {
    log.info("Initializing default data for tenant {} in schema {}", tenant.getName(), schemaName);
  }

  private void createSchema(final String schemaName) {
    final String sql = "CREATE SCHEMA IF NOT EXISTS " + schemaName;
    this.jdbcTemplate.execute(sql);
  }

  private void runTenantMigrations(String schemaName) {
    log.info("Running Flyway migrations for schema {}", schemaName);
    final Flyway tenantFlyway = Flyway.configure()
        .dataSource(this.dataSource)
        .schemas(schemaName)
        .locations("classpath:db/migration/tenants")
        .baselineOnMigrate(true)
        .table("flyway_schema_history")
        .validateOnMigrate(true)
        .cleanDisabled(true)
        .load();

    log.info("Starting Flyway migration for schema {}", schemaName);
    tenantFlyway.migrate();
    log.info("Flyway migration completed for schema {}", schemaName);
  }

}
