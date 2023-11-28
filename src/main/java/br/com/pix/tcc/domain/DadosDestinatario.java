package br.com.pix.tcc.domain;

import lombok.Data;

@Data
public class DadosDestinatario {

    String banco_destinatario;
    String cpf_destinatario;
    String nome_destinatario;
    Integer numero_conta_destinatario;

    Boolean rastreavel;

    String erro;


}
