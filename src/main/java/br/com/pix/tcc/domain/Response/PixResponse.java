package br.com.pix.tcc.domain.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.format.DateTimeFormatter;

@Data
public class PixResponse {

    private Float valortransferencia;
    private DateTimeFormatter dataHoraTransferencia;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String  nomeDestinatario;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String  nomeRemetente;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cpfRemetente;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cpfDestinatario;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mensagem;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int codigo;


}
