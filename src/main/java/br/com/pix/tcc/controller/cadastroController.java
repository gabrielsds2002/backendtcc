package br.com.pix.tcc.controller;

import br.com.pix.tcc.business.ValidadorCPF_CNPJ;
import br.com.pix.tcc.dao.ClienteDAO;
import br.com.pix.tcc.domain.Response.LoginResponse;
import br.com.pix.tcc.domain.request.CadastroRequest;
import br.com.pix.tcc.domain.Response.CadastroReponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class cadastroController {

    ValidadorCPF_CNPJ validadorCPFCnpj;

    @PostMapping("/cadastro")
    public CadastroReponse saveCadastro(@RequestBody CadastroRequest cadastro) {
        CadastroReponse cadastroReponse = new CadastroReponse();
        ClienteDAO clienteDAO = new ClienteDAO();



        try {
            String valida;
            valida= ValidadorCPF_CNPJ.validarCPF(cadastro.getCpf_cnpj());
            if(valida == "Valor valido") {
                cadastro.setCpf_cnpj(ValidadorCPF_CNPJ.limparCaracteresEspeciais(cadastro.getCpf_cnpj()));
                if (clienteDAO.validaCadastro(cadastro) == true) {
                    cadastroReponse.setMensagem(clienteDAO.cria(cadastro));
                    cadastroReponse.setCodigo(HttpStatus.OK);
                    return cadastroReponse;
                } else {
                    return obterRespostaErro("CPF ja cadastrado", HttpStatus.UNPROCESSABLE_ENTITY);
                }

            }else{
                return obterRespostaErro(valida, HttpStatus.UNPROCESSABLE_ENTITY);

            }
        } catch (Exception e) {
            return obterRespostaErro("Serviço indisponível", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private CadastroReponse obterRespostaErro(String msg, HttpStatus status) {
        CadastroReponse response;
        CadastroReponse obterResposta = new CadastroReponse();
        obterResposta.setMensagem(msg);
        obterResposta.setCodigo(HttpStatus.valueOf(status.value()));
        response = obterResposta;
        return response;
    }

}
