package br.com.pix.tcc.config;

import io.jsonwebtoken.*;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Date;

public class JwtGenerator {

    SecretKey key = null;
    SecretKey secretKey = KeyGenerator.getInstance("HmacSHA256").generateKey();

    public JwtGenerator() throws NoSuchAlgorithmException {
    }

    public static String getToken(String username) throws NoSuchAlgorithmException {
        // Chave secreta para assinar o token (mantenha isso em segredo)

        SecretKey secretKey = KeyGenerator.getInstance("HmacSHA256").generateKey();

        // Convertendo a chave para uma representação de string (Base64, por exemplo)
        String encodedKey = "bUqLtZEWDkkE5Tw6p6zh0qvI3nZDqgXapjo/JsBlN10=";


        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + 3600000); // 1 hora de expiração

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(agora)
                .setExpiration(expiracao)
                .signWith(SignatureAlgorithm.HS256, encodedKey)
                .compact();
    }


    // Informações a serem incluídas no payload


    public static String validaToken(String token, String user) {
        String newToken =token.replace("Bearer ","");
        try {
            Claims claims = validateToken(newToken);

            // A validação foi bem-sucedida
            System.out.println("Token válido para o usuário: " + claims.getSubject());
            System.out.println("Tempo de expiração: " + claims.getExpiration());
            if(claims.getSubject().equals(user)){
                return "Token valido";
            }else {
                return "Token inválido";

            }
        } catch (ExpiredJwtException e) {
            return "Token expirado";
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            return "Token inválido";
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public static Claims validateToken(String token) throws NoSuchAlgorithmException {
        String encodedKey = "bUqLtZEWDkkE5Tw6p6zh0qvI3nZDqgXapjo/JsBlN10=";
        return Jwts.parser().setSigningKey(encodedKey).parseClaimsJws(token).getBody();
    }
}
