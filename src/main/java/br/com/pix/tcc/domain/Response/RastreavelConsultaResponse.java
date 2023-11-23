package br.com.pix.tcc.domain.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RastreavelConsultaResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cpfCnpj;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String idTransferencia;
}
