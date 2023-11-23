package br.com.pix.tcc.domain.request;

import jakarta.persistence.Entity;
import lombok.Data;

import javax.validation.constraints.Size;

@Entity
@Data
public class PixRequest {

    @Size(min = 12, max = 15)
    private int cpf_remetente;

    @Size(min = 3, max = 50)
    private String  nome_remetente;

    @Size(min = 3, max = 15)
    private String chave_pix_remetente;

    private String banco_remetente;
    private String banco_destinatario;

    @Size(min = 3, max = 50)
    private int cpf_destinatario;

    @Size(min = 3, max = 50)
    private String  nome_Destinatario;

    private String observacao;
    @Size(min = 3, max = 50)
    private String  chave_pix_destinatario;
    private Float valor_transferencia;

    private String localizacao_ransferencia;

    private String tipo_transferencia;

    private int numero_conta_pagador;
    private int numero_conta_remetente;

    private String idTransferencia;
    private Boolean rastreavel;

    private String senha;


    private float limite_diario;

    private float limite_noturno;

}
