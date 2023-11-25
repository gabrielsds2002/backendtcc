package br.com.pix.tcc.domain.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ConsultaPrePixRequest {



    String cpf_cnpj;


}
