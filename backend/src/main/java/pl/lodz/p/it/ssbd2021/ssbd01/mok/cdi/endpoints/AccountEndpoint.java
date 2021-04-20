package pl.lodz.p.it.ssbd2021.ssbd01.mok.cdi.endpoints;

import java.text.ParseException;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2021.ssbd01.entities.PatientData;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.ejb.managers.AccountManager;
import pl.lodz.p.it.ssbd2021.ssbd01.security.JwtUtils;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.converters.AccountConverter;


/**
 * Typ Account endpoint.
 */
@Path("account")
@RequestScoped
public class AccountEndpoint {

    @EJB
    private JwtUtils jwtUtils;

    @Inject
    private AccountManager accountManager;


    /**
     * Tworzy nowe konto.
     *
     * @param accountDto obiekt zawierający login, email, hasło i inne wymagane dane
     */
    @POST
    @Path("create")
    @Consumes({MediaType.APPLICATION_JSON})
    public void createAccount(AccountDto accountDto) {
        accountManager.createAccount(AccountConverter.createAccountEntityFromDto(accountDto), new PatientData());
    }


    /**
     * Confirm account.
     *
     * @param jwt jwt
     */
    // localhost:8181/ssbd01-0.0.7-SNAPSHOT/api/account/confirm/{jwt}
    @PUT
    @Path("confirm/{jwt}")
    @Produces({MediaType.APPLICATION_JSON})
    public void confirmAccount(@PathParam("jwt") String jwt) {
        if (jwtUtils.validateRegistrationConfirmationJwtToken(jwt)) {
            try {
                accountManager.confirmAccount(jwtUtils.getUserNameFromRegistrationConfirmationJwtToken(jwt));
            } catch (ParseException e) {
                // TODO: 18.04.2021
                e.printStackTrace();
            }
        }
    }

}
