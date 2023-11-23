package br.com.pix.tcc.domain.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class RastreavelResponse {


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cpfCnpj;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cpfCnpjDestinatario;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String localizacaoTransferencia;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String chavePixDestinatario;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String dataTransferencia;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String horaTransderencia;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String tipoTransferencia;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String idTransferencia;



}
