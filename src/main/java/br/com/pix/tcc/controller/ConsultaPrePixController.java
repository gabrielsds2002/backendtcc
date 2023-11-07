package br.com.pix.tcc.controller;

import br.com.pix.tcc.dao.ClienteDAO;
import br.com.pix.tcc.domain.Response.CadastroReponse;
import br.com.pix.tcc.domain.Response.ConsultaPrePixResponse;
import br.com.pix.tcc.domain.Response.LoginResponse;
import br.com.pix.tcc.domain.request.CadastroRequest;
import br.com.pix.tcc.domain.request.ConsultaPrePixRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class ConsultaPrePixController {

    @PostMapping("/consulta")
    public ConsultaPrePixResponse saveCadastro(@RequestBody ConsultaPrePixRequest cadastro) {
        ResponseEntity<LoginResponse> response = null;
        ConsultaPrePixResponse cadastroReponse = new ConsultaPrePixResponse();
        ClienteDAO clienteDAO = new ClienteDAO();



        try {
            //validatoken

           //consulta cliente
           //consulta historico transferencia
                    //consulta dados historico de cliente


        } catch (Exception e) {
            return obterRespostaErro("Serviço indisponível", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    private ConsultaPrePixResponse obterRespostaErro(String msg, HttpStatus status) {
        ConsultaPrePixResponse response;
        ConsultaPrePixResponse obterResposta = new ConsultaPrePixResponse();
        obterResposta.setMensagem(msg);
        obterResposta.setCodigo(HttpStatus.valueOf(status.value()));
        response = obterResposta;
        return response;
    }
}
