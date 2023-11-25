package br.com.pix.tcc.controller;

import br.com.pix.tcc.config.JwtGenerator;
import br.com.pix.tcc.dao.ConsultaSaldoDao;
import br.com.pix.tcc.dao.LoginDao;
import br.com.pix.tcc.domain.Response.ConsultaSaldoResponse;
import br.com.pix.tcc.domain.Response.LoginResponse;
import br.com.pix.tcc.domain.Response.PixResponse;
import br.com.pix.tcc.domain.request.ConsultaSaldoRequest;
import br.com.pix.tcc.domain.request.LoginRequest;
import br.com.pix.tcc.domain.Response.CadastroReponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
public class loginController {



    @GetMapping("/login")
    public ResponseEntity login(@Valid LoginRequest loginRequest) throws NoSuchAlgorithmException {
        LoginResponse loginResponse = new LoginResponse();
        LoginDao loginDao = new LoginDao();
        loginResponse=loginDao.login(loginRequest);
        if(loginResponse.getStatus() == true){
            String token =JwtGenerator.getToken(loginRequest.getCpf_cnpj());
            return ResponseEntity.ok(token);
        }else{
            // Retornando um ResponseEntity com status HTTP Bad Request (400) e corpo JSON
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(obterRespostaErro(loginResponse.getMensagem(),HttpStatus.BAD_REQUEST));
        }
    }

    @GetMapping("/saldo")
    public ResponseEntity consultaSaldo(ConsultaSaldoRequest consultaSaldo){
        ConsultaSaldoResponse response = new ConsultaSaldoResponse();
        response =ConsultaSaldoDao.consultaSaldo(consultaSaldo.getCpf_cnpj());
        if (response.getCodigo() ==HttpStatus.OK)
        return ResponseEntity.ok(response);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(obterRespostaErro(response.getMensagem(),HttpStatus.BAD_REQUEST));

    }


    private LoginResponse obterRespostaErro(String msg, HttpStatus status) {
        LoginResponse response;
        LoginResponse obterResposta = new LoginResponse();
        obterResposta.setMensagem(msg);
        obterResposta.setCodigo(HttpStatus.valueOf(status.value()));
        response = obterResposta;
        return response;
    }
}
