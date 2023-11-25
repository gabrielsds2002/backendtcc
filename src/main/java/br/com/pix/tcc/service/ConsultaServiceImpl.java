package br.com.pix.tcc.service;

import br.com.pix.tcc.business.ValidacaoRemocaoCPFDuplicado;
import br.com.pix.tcc.dao.ConsultaPrePixDAO;
import br.com.pix.tcc.domain.CpfJaEnviado;
import br.com.pix.tcc.domain.Response.ConsultaDestinatarioResponse;
import br.com.pix.tcc.domain.Response.ConsultaPrePixResponse;
import br.com.pix.tcc.domain.Response.LoginResponse;
import br.com.pix.tcc.domain.request.ConsultaPrePixRequest;
import br.com.pix.tcc.domain.request.RastreavelRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultaServiceImpl implements Consultaservice {


    ValidacaoRemocaoCPFDuplicado validacaoRemocaoCPFDuplicado;


    @Override
    public ConsultaPrePixResponse consulta(ConsultaPrePixRequest request) {
        ResponseEntity<LoginResponse> response = null;
        ConsultaPrePixResponse cadastroReponse = new ConsultaPrePixResponse();
        List<CpfJaEnviado> cpfJaEnviado = new ArrayList<>();
        ConsultaPrePixDAO consulta = new ConsultaPrePixDAO();
        try {
            //validatoken

            cadastroReponse = consulta.validaCadastro(request);
            if (cadastroReponse.getCodigo() == HttpStatus.OK) {
                cpfJaEnviado = consulta.historico(request);
                List<CpfJaEnviado> cpfsSemDuplicatas = validacaoRemocaoCPFDuplicado.removerCPFDuplicado(cpfJaEnviado);
                if (cpfsSemDuplicatas != null) {
                    //validacaoRemocaoCPFDuplicado.removerCPFDuplicado(cpfJaEnviado);
                    cadastroReponse.setCpfJaEnviado(cpfsSemDuplicatas);
                    return cadastroReponse;
                } else {
                    return cadastroReponse;
                }
            } else {
                cadastroReponse.setMensagem("erro ao consultar historico");
                cadastroReponse.getCodigo();
                return cadastroReponse;
            }


        } catch (Exception e) {
            cadastroReponse.setMensagem("Serviço indisponível");
            cadastroReponse.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR);
            return cadastroReponse;
        }
    }

    @Override
    public ConsultaDestinatarioResponse consultaRemetente(ConsultaPrePixRequest request) {
        ResponseEntity<LoginResponse> response = null;
        ConsultaDestinatarioResponse cadastroReponse = new ConsultaDestinatarioResponse();
        ConsultaPrePixDAO consulta = new ConsultaPrePixDAO();

        cadastroReponse = consulta.validaRemetente(request);
        return cadastroReponse;
    }
}
