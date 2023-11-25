package br.com.pix.tcc.domain.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErroResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mensagem;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HttpStatus codigo;
}
