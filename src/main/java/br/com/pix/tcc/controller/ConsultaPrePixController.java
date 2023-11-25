package br.com.pix.tcc.controller;

import br.com.pix.tcc.business.ValidadorCPF_CNPJ;
import br.com.pix.tcc.config.BaseResponse;
import br.com.pix.tcc.config.JwtGenerator;
import br.com.pix.tcc.dao.ClienteDAO;
import br.com.pix.tcc.domain.Response.CadastroReponse;
import br.com.pix.tcc.domain.Response.ConsultaPrePixResponse;
import br.com.pix.tcc.domain.Response.ErroResponse;
import br.com.pix.tcc.domain.Response.LoginResponse;
import br.com.pix.tcc.domain.data.ConsultaPrePixData;
import br.com.pix.tcc.domain.request.CadastroRequest;
import br.com.pix.tcc.domain.request.ConsultaDestinatario;
import br.com.pix.tcc.domain.request.ConsultaPrePixRequest;
import br.com.pix.tcc.domain.request.RastreavelRequest;
import br.com.pix.tcc.service.Consultaservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ConsultaPrePixController {

    private final Consultaservice consultaservice;
    @PostMapping("/consulta")
    public ResponseEntity consultaDados(@RequestHeader("Authorization") String token,
                                                  @RequestBody ConsultaPrePixRequest request) {
        String valida;
        valida= ValidadorCPF_CNPJ.validarCPF(request.getCpf_cnpj());
        if(valida == "Valor valido") {
            request.setCpf_cnpj(ValidadorCPF_CNPJ.limparCaracteresEspeciais(request.getCpf_cnpj()));
            String getToken = JwtGenerator.validaToken(token, request.getCpf_cnpj());
            if (getToken == "Token valido") {
                return ResponseEntity.ok(BaseResponse.ok(consultaservice.consulta(request)));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(obterRespostaErro(getToken, HttpStatus.UNAUTHORIZED));
            }
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(obterRespostaErro(valida, HttpStatus.UNPROCESSABLE_ENTITY));
        }
    }

    @GetMapping("/consulta_destinatario")
    public ResponseEntity ConsultaDadosEnvio(@RequestHeader("Authorization") String token,
                                             ConsultaDestinatario request) {
        String valida;
        valida= ValidadorCPF_CNPJ.validarCPF(request.getCpf_cnpj());
        if(valida == "Valor valido") {
            request.setCpf_cnpj(ValidadorCPF_CNPJ.limparCaracteresEspeciais(request.getCpf_cnpj()));
            String getToken = JwtGenerator.validaToken(token, request.getCpf_cnpj());
            if (getToken == "Token valido") {
                return ResponseEntity.ok(BaseResponse.ok(consultaservice.consultaRemetente(request)));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(obterRespostaErro(getToken, HttpStatus.UNAUTHORIZED));
            }
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(obterRespostaErro(valida, HttpStatus.UNPROCESSABLE_ENTITY));
        }
    }

    private ErroResponse obterRespostaErro(String msg, HttpStatus status) {
        ErroResponse response = new ErroResponse();
        response.setMensagem(msg);
        response.setCodigo(HttpStatus.valueOf(status.value()));
        return response;
    }
}
