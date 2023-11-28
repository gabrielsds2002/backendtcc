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

    String banco_remetente;
    Integer numero_conta_pagador;

    String nome;

    Boolean rastreavel;
}
