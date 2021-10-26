package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
/**
 * Klasa narzędzia zalogowanego kotna.
 */
@Stateless
public class LoggedInAccountUtil {
    @Resource(name = "sessionContext")
    private SecurityContext securityContext;

    /**
     * Pobiera login zalogowanego użytkownika.
     *
     * @return login zalogowanego użytkownika
     */
    public String getLoggedInAccountLogin() {
        if (securityContext == null || securityContext.getCallerPrincipal() == null) {
            return null;
        }
        return securityContext.getCallerPrincipal().getName();
    }
}
