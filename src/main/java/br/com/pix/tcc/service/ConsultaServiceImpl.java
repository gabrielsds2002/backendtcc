package br.com.pix.tcc.service;

import br.com.pix.tcc.business.ValidacaoRemocaoCPFDuplicado;
import br.com.pix.tcc.dao.ConsultaPrePixDAO;
import br.com.pix.tcc.domain.CpfJaEnviado;
import br.com.pix.tcc.domain.Response.ConsultaPrePixResponse;
import br.com.pix.tcc.domain.Response.LoginResponse;
import br.com.pix.tcc.domain.request.ConsultaPrePixRequest;
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
        List<CpfJaEnviado> cpfJaEnviado= new ArrayList<>();
        ConsultaPrePixDAO consulta = new ConsultaPrePixDAO();
        try {
            //validatoken

            cadastroReponse= consulta.validaCadastro(request);
            cpfJaEnviado= consulta.historico(request);
            List<CpfJaEnviado> cpfsSemDuplicatas = validacaoRemocaoCPFDuplicado.removerCPFDuplicado(cpfJaEnviado);
            //validacaoRemocaoCPFDuplicado.removerCPFDuplicado(cpfJaEnviado);
            cadastroReponse.setCpfJaEnviado(cpfsSemDuplicatas);
            cadastroReponse.setCodigo(HttpStatus.OK);


        } catch (Exception e) {
            //return obterRespostaErro("Serviço indisponível", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return cadastroReponse;
    }
}
