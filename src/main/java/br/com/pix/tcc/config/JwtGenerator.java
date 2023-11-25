package br.com.pix.tcc.config;

import io.jsonwebtoken.*;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class JwtGenerator {

    SecretKey key = null;

    public static String getToken(String username) throws NoSuchAlgorithmException {
        // Chave secreta para assinar o token (mantenha isso em segredo)

        SecretKey secretKey = KeyGenerator.getInstance("HmacSHA256").generateKey();

        System.out.println(secretKey.getEncoded());
        // Convertendo a chave para uma representação de string (Base64, por exemplo)
        String encodedKey = java.util.Base64.getEncoder().encodeToString(secretKey.getEncoded());


        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + 3600000); // 1 hora de expiração

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(agora)
                .setExpiration(expiracao)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    // Informações a serem incluídas no payload


    public static String validaToken(String token) {


        try {
            //Claims claims = validateToken(token);

            // A validação foi bem-sucedida
//            System.out.println("Token válido para o usuário: " + claims.getSubject());
//            System.out.println("Tempo de expiração: " + claims.getExpiration());
        } catch (ExpiredJwtException e) {
            System.out.println("Token expirado");
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            System.out.println("Token inválido");
        }
        return token;
    }


//    public static Claims validateToken(String token) {
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//    }
}
