package br.com.pix.tcc.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

import static java.security.KeyRep.Type.SECRET;

public class TokenConfig {

    private static final String SECRET = "secretpassword";

    public  String generateJwtToken() {
        Date expirationTime = new Date(System.currentTimeMillis() + 600000); // 10 minutos em milissegundos
        String token = JWT.create()
                .withExpiresAt(expirationTime)
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    public static boolean verifyJwtToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
