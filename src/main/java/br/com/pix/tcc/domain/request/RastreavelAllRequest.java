package br.com.pix.tcc.domain.request;

import lombok.Data;

@Data
public class RastreavelAllRequest {

    String cpf;

    String idTransferencia;
    String token;
}

