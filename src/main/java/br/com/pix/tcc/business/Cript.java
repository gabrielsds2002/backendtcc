package br.com.pix.tcc.business;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Cript {

    private static final String CHAVE = "chave-secreta1234"; // Chave secreta para criptografia, deve ser mantida segura

    public static String encryptIntToBase64(String number) throws Exception {
        String encodedString = Base64.getEncoder().encodeToString(number.getBytes());
        return encodedString;
    }

    public static String decryptIntFromBase64(String senha) throws Exception {
        byte[] decodedBytes = Base64.getDecoder().decode(senha);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }
}
