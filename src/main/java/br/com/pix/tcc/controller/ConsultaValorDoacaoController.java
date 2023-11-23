package br.com.pix.tcc.controller;

import br.com.pix.tcc.dao.DoacaoDao;
import br.com.pix.tcc.dao.LoginDao;
import br.com.pix.tcc.domain.Response.AtualizaMesResponse;
import br.com.pix.tcc.domain.Response.ConsultaResponse;
import br.com.pix.tcc.domain.Response.DoacaoResponse;
import br.com.pix.tcc.domain.Response.LoginResponse;
import br.com.pix.tcc.domain.request.AtualizaMesRequest;
import br.com.pix.tcc.domain.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConsultaValorDoacaoController {


    @GetMapping("/doacao")
    public DoacaoResponse consulta(LoginRequest loginRequest){
        DoacaoDao doacaoDao = new DoacaoDao();
        return doacaoDao.consultaSaldoDoacaoMes();
    }

    @GetMapping("/doacao_mes")
    public List<ConsultaResponse> consultaMesAll(AtualizaMesRequest atualizaMesRequest){
        DoacaoDao doacaoDao = new DoacaoDao();
        return doacaoDao.consultaDoacaoAll();
    }

    @GetMapping("/add_doacao")
    public AtualizaMesResponse addValor(AtualizaMesRequest atualizaMesRequest){
        DoacaoDao doacaoDao = new DoacaoDao();
        return doacaoDao.atualizaSaldoDoacaoMes(atualizaMesRequest);
    }
}
