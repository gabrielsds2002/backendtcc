package br.com.pix.tcc.domain.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaSaldoResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private float saldo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mensagem;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HttpStatus codigo;
}
