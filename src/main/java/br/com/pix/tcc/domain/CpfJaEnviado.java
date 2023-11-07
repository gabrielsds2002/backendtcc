package br.com.pix.tcc.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CpfJaEnviado {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer cpf_cnpj;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nome;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String chave_pix;


    private String bancoDestinatario;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String numeroContaPagador;
}
