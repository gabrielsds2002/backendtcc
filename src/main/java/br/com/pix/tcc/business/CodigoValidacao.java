package br.com.pix.tcc.business;

import java.security.SecureRandom;
import java.util.Random;

public class CodigoValidacao {


    public static String gerarCodigoValidacao(int comprimento) {
        String CARACTERES_VALIDOS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random RANDOM = new SecureRandom();
        StringBuilder codigoValidacao = new StringBuilder(comprimento);
        for (int i = 0; i < comprimento; i++) {
            int indiceAleatorio = RANDOM.nextInt(CARACTERES_VALIDOS.length());
            codigoValidacao.append(CARACTERES_VALIDOS.charAt(indiceAleatorio));
        }
        return codigoValidacao.toString();
    }
}
