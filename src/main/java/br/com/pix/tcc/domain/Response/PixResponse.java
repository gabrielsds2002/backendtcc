package br.com.pix.tcc.domain.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PixResponse {

    private Float valortransferencia;
    private LocalTime dataHoraTransferencia;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String  nomeDestinatario;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String  nomeRemetente;

    @Size(min = 12, max = 15)
    private int cpfRemetente;

    @Size(min = 3, max = 50)
    private int cpfDestinatario;

    @Size(min = 3, max = 50)
    private String codigoValidacao;

    private String mensagem;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HttpStatus codigo;


}
