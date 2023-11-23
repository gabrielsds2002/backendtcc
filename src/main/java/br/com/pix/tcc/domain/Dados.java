package br.com.pix.tcc.domain;

import lombok.Data;

@Data
public class Dados {


    String senha;

    String senhaSeguranca;

    String erro;

    Float saldo;

    Float limiteDiario;

    Float limiteNoturno;

}
