package br.com.pix.tcc.domain.request;

import lombok.Data;

@Data
public class AtualizaMesRequest {

    float Saldo;
    String mes;
    String ano;
}
