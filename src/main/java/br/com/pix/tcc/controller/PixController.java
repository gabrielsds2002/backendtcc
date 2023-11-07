package br.com.pix.tcc.controller;

import br.com.pix.tcc.dao.PixDao;
import br.com.pix.tcc.dao.TransferenciaDAO;
import br.com.pix.tcc.domain.Response.CadastroReponse;
import br.com.pix.tcc.domain.Response.PixResponse;
import br.com.pix.tcc.domain.Dados;
import br.com.pix.tcc.domain.request.PixRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalTime;

public class PixController {

    Dados dados;



    @GetMapping("/login")
    public String login(PixRequest pixRequest) {
        ResponseEntity<PixResponse> response = null;
        CadastroReponse cadastroReponse = null;

        PixDao pixDao = new PixDao();
        LocalTime horaAtual = LocalTime.now();
        LocalTime horarioNoite = LocalTime.of(18, 00);


        String msgErro = "CPF já cadastrado";
        try {
            //consulta clientes
            if (pixDao.consulta_clientes(pixRequest) == true) {
                dados = pixDao.getDados(pixRequest.getCpfRemetente());
                if (horaAtual.isAfter(horarioNoite)) {
                    if (pixRequest.getSenha() == dados.getSenha()) {
                        tranferencia(pixRequest);
                    } else if (pixRequest.getSenha() == dados.getSenhaSeguranca()) {
                        tranferencia(pixRequest);

                    } else {
                        String erro = "senha invalida";
                    }
                }
            }
        } catch (Exception e) {
        }
        return "Teste";
    }


    public void tranferencia(PixRequest pixRequest) {
        TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
        transferenciaDAO.pixResponse(pixRequest);
        transferenciaDAO.atualizaHistorico(pixRequest);
        transferenciaDAO.atualizarSaldoSoma(pixRequest.getCpfDestinatario(),pixRequest.getNumeroContaRemetente(),pixRequest.getValortransferencia());
        transferenciaDAO.atualizarSaldo(pixRequest.getCpfRemetente(),pixRequest.getNumeroContaRemetente(),pixRequest.getValortransferencia());

        //return pixDaoDao.pix(pixRequest);
    }

    public void rastreavel(PixRequest pixRequest) {
        PixDao pixDao = new PixDao();
        if (pixDao.consulta_rastreavel(pixRequest.getCpfRemetente()) == false || pixDao.consulta_rastreavel(pixRequest.getCpfDestinatario()) == false) {

            //return pixDaoDao.pix(pixRequest);
        } else {

            //atualiza tabela rastreavel
            //request enviaLocalizacao
            //return pixDaoDao.pix(pixRequest);
        }
    }



}
