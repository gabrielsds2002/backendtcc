package br.com.pix.tcc.controller;

import br.com.pix.tcc.config.BaseResponse;
import br.com.pix.tcc.dao.ClienteDAO;
import br.com.pix.tcc.domain.Response.CadastroReponse;
import br.com.pix.tcc.domain.Response.ConsultaPrePixResponse;
import br.com.pix.tcc.domain.Response.LoginResponse;
import br.com.pix.tcc.domain.data.ConsultaPrePixData;
import br.com.pix.tcc.domain.request.CadastroRequest;
import br.com.pix.tcc.domain.request.ConsultaPrePixRequest;
import br.com.pix.tcc.service.Consultaservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConsultaPrePixController {

    private final Consultaservice consultaservice;
    @PostMapping("/consulta")
    public ResponseEntity<BaseResponse> saveCadastro(@RequestBody ConsultaPrePixRequest request) {
        return ResponseEntity.ok(BaseResponse.ok(consultaservice.consulta(request)));
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
