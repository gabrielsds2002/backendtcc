package br.com.pix.tcc.business;

public class ValidaChavePix {


    public static String verificarTipoChavePix(String chavePix) {
        if (chavePix.matches("\\d{11}")) {
            return "CPF";
        } else if (chavePix.matches("\\d{14}")) {
            return "CNPJ";
        }else if (chavePix.matches("\\d{11,12}")) {
            return "Telefone";
        } else if (chavePix.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            return "E-mail";
        } else {
            return "Tipo de chave não identificado";
        }
    }
}

