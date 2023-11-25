package br.com.pix.tcc.business;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Formata {

    public static String formatarLocalTime(LocalTime localTime) {
        // Criar um formato personalizado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Formatar o LocalTime usando o formato
        return localTime.format(formatter);
    }

    public static String formatarLocalDate(LocalDate localDate) {
        // Criar um formato personalizado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Formatar o LocalDate usando o formato
        return localDate.format(formatter);
    }

    public static String formatarCPF(String cpf) {
        if (cpf.length() ==11){
            return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");

        } else if (cpf.length() ==14) {
            return cpf.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
        }
        return cpf;
    }

}
