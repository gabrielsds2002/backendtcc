package br.com.pix.tcc.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CpfJaEnviado {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer cpf_cnpj;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nome;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String chave_pix;









}
