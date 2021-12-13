package pl.lodz.p.it.ssbd2021.ssbd01.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;

/**
 * Abstrakcyjna klasa Jwt utils.
 */
public abstract class JwtUtilsAbstract {

    /**
     * Pobiera pole jwt expiration.
     *
     * @return jwt expiration
     */
    protected abstract Long getJwtExpiration();

    /**
     * Pobiera pole jwt secret.
     *
     * @return jwt secret
     */
    protected abstract String getJwtSecret();


    /**
     * Parsowanie HttpServletRequest na token JWT do autoryzacji.
     *
     * @param request request
     * @return string
     */
    public String parseAuthJwtFromHttpServletRequest(HttpServletRequest request) {
        System.out.println("parseAuthJwtFromHttpServletRequest_______________");
        String headerAuth = request.getHeader("Authorization");

        if (headerAuth != null && headerAuth.length() > 0 && !headerAuth.isBlank() && headerAuth.startsWith("Bearer ")) {
            System.out.println(headerAuth.substring(7));
            return headerAuth.substring(7);
            
        }

        return null;
    }

    /**
     * Generuje token JWT na potrzeby weryfikacji rejestracji.
     *
     * @param username username
     * @return JWT token
     */
    protected String generateJwtTokenForUsername(String username) {
        try {
            final JWSSigner signer = new MACSigner(getJwtSecret());
            final JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .expirationTime(new Date(new Date().getTime() + getJwtExpiration()))
                    .build();
            final SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS384), claimsSet);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
            return "JWT error";
        }
    }

    /**
     * Pobiera login użytkownika z tokenu JWT wydanego na potrzebę weryfikacji konta po rejestracji.
     *
     * @param token JWT token
     * @return Login użytkownika o zadanym tokenie
     * @throws ParseException ParseException
     */
    public String getUserNameFromJwtToken(String token) throws ParseException {
        return SignedJWT.parse(token).getJWTClaimsSet().getSubject();
    }

    /**
     * Metoda sprawdzająca token jwt na potrzeby weryfikacji konta poprzez email.
     *
     * @param tokenToValidate JWT token
     * @return boolean
     */
    protected boolean validateJwtToken(String tokenToValidate) {
        System.out.println(tokenToValidate);
        try {
            JWSObject jwsObject = JWSObject.parse(tokenToValidate);
            JWSVerifier jwsVerifier = new MACVerifier(getJwtSecret());
            var expirationTime = SignedJWT.parse(tokenToValidate).getJWTClaimsSet().getExpirationTime();
            return jwsObject.verify(jwsVerifier) && expirationTime.after(new Date(System.currentTimeMillis()));
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Generuje token JWT na potrzeby weryfikacji resetu hasła.
     *
     * @param username username
     * @return JWT token
     */
    protected String generateJwtTokenForUsernameAndVersion(String username, long version) {
        try {
            final JWSSigner signer = new MACSigner(getJwtSecret());
            final JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(username + "/" + version)
                    .expirationTime(new Date(new Date().getTime() + getJwtExpiration()))
                    .build();
            final SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS384), claimsSet);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
            // TODO: 18.04.2021
            return "JWT error";
        }
    }

    /**
     * Pobiera login użytkownika z tokenu JWT wydanego na potrzebę resetu hasła.
     *
     * @param token JWT token
     * @return Login użytkownika o zadanym tokenie
     * @throws ParseException ParseException
     */
    public String getVersionAndNameFromJwtToken(String token) throws ParseException {
        return SignedJWT.parse(token).getJWTClaimsSet().getSubject();
    }
}
