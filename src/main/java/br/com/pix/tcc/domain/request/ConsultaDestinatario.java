package br.com.pix.tcc.domain.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ConsultaDestinatario {

    String chavepix;

    @NotBlank(message = "Digite um nome valido")
    @Size(min = 12, max = 14)
    @NotNull
    private String cpf_cnpj;
}
