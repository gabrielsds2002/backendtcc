package br.com.pix.tcc.domain.Response;

import br.com.pix.tcc.domain.CpfJaEnviado;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ConsultaPrePixResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<CpfJaEnviado>CpfJaEnviado;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private float saldo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nome;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cpf_cnpj;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String chave_pix;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private float limite_diario;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private float limite_noturno;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String instituicao_financeira;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String numeroContaRemetente;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HttpStatus codigo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mensagem;




}
