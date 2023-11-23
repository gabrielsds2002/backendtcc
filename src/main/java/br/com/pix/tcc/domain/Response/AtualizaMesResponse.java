package br.com.pix.tcc.domain.Response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class AtualizaMesResponse {


    String mensagem;

    HttpStatus codigo;
}
