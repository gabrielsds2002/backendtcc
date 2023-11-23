package br.com.pix.tcc.domain.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ConsultaSaldoRequest {

    @NotBlank(message = "Digite um nome valido")
    @Size(min = 12, max = 15)
    int cpf_cnpj;
}
