package br.com.pix.tcc.controller;

import br.com.pix.tcc.dao.ConsultaSaldoDao;
import br.com.pix.tcc.dao.LoginDao;
import br.com.pix.tcc.domain.Response.ConsultaSaldoResponse;
import br.com.pix.tcc.domain.Response.LoginResponse;
import br.com.pix.tcc.domain.request.ConsultaSaldoRequest;
import br.com.pix.tcc.domain.request.LoginRequest;
import br.com.pix.tcc.domain.Response.CadastroReponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class loginController {



    @GetMapping("/login")
    public LoginResponse login(LoginRequest loginRequest){
        LoginDao loginDao = new LoginDao();
        return loginDao.login(loginRequest);
    }

    @GetMapping("/saldo")
    public ConsultaSaldoResponse consultaSaldo(ConsultaSaldoRequest consultaSaldo){
        ConsultaSaldoResponse response = new ConsultaSaldoResponse();
        response.setSaldo(ConsultaSaldoDao.consultaSaldo(consultaSaldo.getCpf_cnpj()));
        return response;
    }
}
