package br.com.pix.tcc.business;

import java.util.InputMismatchException;

public class ValidadorCPF_CNPJ {


    private static final int LENGTH_CPF = 11;
    private static final int LENGTH_CNPJ = 14;

    public static String limparCaracteresEspeciais(String documento) {
        if (documento.contains(".")) {
            documento = documento.replace(".", "");
        }
        if (documento.contains("-")) {
            documento = documento.replace("-", "");
        }
        if (documento.contains("/")) {
            documento = documento.replace("/", "");
        }
        return documento;
    }


    public static String validarCPF(String cpf) {


        cpf = limparCaracteresEspeciais(cpf);
        System.out.println(cpf.length());
        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() == LENGTH_CPF) {
            if (!isCpf(cpf)) {
                return "CPF invalido";
            }
        }else {
            if (cpf.length() == LENGTH_CNPJ) {
                if (!isCnpj(cpf)) {
                    return "CNPJ invalido";
                }
            }
        }
        return "Valor valido";
    }


    public static boolean isCpf(String cpf) {

        if (cpf.equals("00000000000") ||
                cpf.equals("11111111111") || cpf.equals("22222222222") ||
                cpf.equals("33333333333") || cpf.equals("44444444444") ||
                cpf.equals("55555555555") || cpf.equals("66666666666") ||
                cpf.equals("77777777777") || cpf.equals("88888888888") ||
                cpf.equals("99999999999") || (cpf.length() != LENGTH_CPF))
            return (false);

        if (cpf.length() != 11) {
            return false;
        }

        int[] digitos = new int[11];
        for (int i = 0; i < 11; i++) {
            digitos[i] = Character.getNumericValue(cpf.charAt(i));
        }

        // Calcular primeiro dígito verificador
        int dv1 = calcularDigitoVerificador(digitos, 9);
        if (dv1 != digitos[9]) {
            return false;
        }

        // Calcular segundo dígito verificador
        int dv2 = calcularDigitoVerificador(digitos, 10);
        return dv2 == digitos[10];
    }

    private static int calcularDigitoVerificador(int[] digitos, int multiplicador) {
        int soma = 0;
        for (int i = 0; i < multiplicador; i++) {
            soma += digitos[i] * (multiplicador + 1 - i);
        }

        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }


    public static boolean isCnpj(String cnpj) {

        if (cnpj.equals("00000000000000") ||
                cnpj.equals("11111111111111") || cnpj.equals("22222222222222") ||
                cnpj.equals("33333333333333") || cnpj.equals("44444444444444") ||
                cnpj.equals("55555555555555") || cnpj.equals("66666666666666") ||
                cnpj.equals("77777777777777") || cnpj.equals("88888888888888") ||
                cnpj.equals("99999999999999") || (cnpj.length() != LENGTH_CNPJ))
            return (false);

        if (cnpj.length() != 14) {
            return false;
        }

        int[] digitos = new int[14];
        for (int i = 0; i < 14; i++) {
            digitos[i] = Character.getNumericValue(cnpj.charAt(i));
        }

        // Calcular primeiro dígito verificador
        int dv1 = calcularDigitoVerificadorCNPJ(digitos, 5);
        if (dv1 != digitos[12]) {
            return false;
        }

        // Calcular segundo dígito verificador
        int dv2 = calcularDigitoVerificadorCNPJ(digitos, 6);
        return dv2 == digitos[13];
    }

    private static int calcularDigitoVerificadorCNPJ(int[] digitos, int multiplicador) {
        int soma = 0;
        for (int i = 0; i < multiplicador; i++) {
            soma += digitos[i] * (multiplicador + 1 - i);
        }

        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }


}
