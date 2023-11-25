package br.com.pix.tcc.controller;

import br.com.pix.tcc.business.Formata;
import br.com.pix.tcc.business.ValidadorCPF_CNPJ;
import br.com.pix.tcc.config.JwtGenerator;
import br.com.pix.tcc.dao.ConsultaSaldoDao;
import br.com.pix.tcc.dao.LoginDao;
import br.com.pix.tcc.domain.Response.*;
import br.com.pix.tcc.domain.request.ConsultaSaldoRequest;
import br.com.pix.tcc.domain.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
public class loginController {


    Formata formata;

    @GetMapping("/login")
    public ResponseEntity login(@Valid LoginRequest loginRequest) throws NoSuchAlgorithmException {
        String valida;
        valida= ValidadorCPF_CNPJ.validarCPF(loginRequest.getCpf_cnpj());
        if(valida == "Valor valido") {
            LoginResponse loginResponse = new LoginResponse();
            LoginDao loginDao = new LoginDao();
            loginResponse = loginDao.login(loginRequest);
            if (loginResponse.getStatus() == true) {
                String token = JwtGenerator.getToken(loginRequest.getCpf_cnpj());
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(obterRespostaErro(loginResponse.getMensagem(), HttpStatus.BAD_REQUEST));
            }
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(obterRespostaErro(valida, HttpStatus.UNPROCESSABLE_ENTITY));
        }
    }

    @GetMapping("/saldo")
    public ResponseEntity consultaSaldo(@RequestHeader ("Authorization") String token, ConsultaSaldoRequest consultaSaldo){
        String valida;
        valida= ValidadorCPF_CNPJ.validarCPF(consultaSaldo.getCpf_cnpj());
        if(valida == "Valor valido") {
            consultaSaldo.setCpf_cnpj(ValidadorCPF_CNPJ.limparCaracteresEspeciais(consultaSaldo.getCpf_cnpj()));
            String getToken = JwtGenerator.validaToken(token, consultaSaldo.getCpf_cnpj());
            if (getToken == "Token valido") {
                ConsultaSaldoResponse response = new ConsultaSaldoResponse();
                response = ConsultaSaldoDao.consultaSaldo(consultaSaldo.getCpf_cnpj());
                response.setCpf_cnpj(formata.formatarCPF(response.getCpf_cnpj()));
                if (response.getCodigo() == HttpStatus.OK)
                    return ResponseEntity.ok(response);
                else
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(obterRespostaErro(response.getMensagem(), HttpStatus.BAD_REQUEST));
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
