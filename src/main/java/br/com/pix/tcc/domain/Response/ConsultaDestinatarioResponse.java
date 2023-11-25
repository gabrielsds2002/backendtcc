package br.com.pix.tcc.domain.Response;

import br.com.pix.tcc.domain.CpfJaEnviado;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
public class ConsultaDestinatarioResponse {


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nome;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cpf_cnpj;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String chave_pix;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String instituicao_financeira;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String numeroContaRemetente;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HttpStatus codigo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mensagem;
}
