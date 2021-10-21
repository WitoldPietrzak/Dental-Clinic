package pl.lodz.p.it.ssbd2021.ssbd01.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

/**
 * Typ Identity store config klasa deklarująca role aplikacyjne.
 */
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:jboss/jdbc/ssbd01auth",
        callerQuery = "#{'select password from glassfish_auth_view where login = ?'}",
        groupsQuery = "select level from glassfish_auth_view  where login = ?",
        hashAlgorithm = AuthHashImpl.class
)

//@DeclareRoles({I18n.RECEPTIONIST, I18n.DOCTOR, I18n.ADMIN, I18n.PATIENT})
@ApplicationScoped
public class IdentityStoreConfig {
}
