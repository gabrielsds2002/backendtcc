package br.com.pix.tcc.controller;

import br.com.pix.tcc.business.ValidaChavePix;
import br.com.pix.tcc.business.ValidacaoRemocaoCPFDuplicado;
import br.com.pix.tcc.business.ValidadorCPF_CNPJ;
import br.com.pix.tcc.config.JwtGenerator;
import br.com.pix.tcc.dao.ConsultaPrePixDAO;
import br.com.pix.tcc.domain.CpfJaEnviado;
import br.com.pix.tcc.domain.Response.*;
import br.com.pix.tcc.domain.request.ConsultaDestinatario;
import br.com.pix.tcc.domain.request.ConsultaPrePixRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConsultaPrePixController {


    ValidacaoRemocaoCPFDuplicado validacaoRemocaoCPFDuplicado;

    @PostMapping("/consulta")
    public ResponseEntity consultaDados(@RequestHeader("Authorization") String token,
                                        @RequestBody ConsultaPrePixRequest request) {

        ResponseEntity<LoginResponse> response = null;
        ConsultaPrePixResponse cadastroReponse = new ConsultaPrePixResponse();
        List<CpfJaEnviado> cpfJaEnviado = new ArrayList<>();
        ConsultaPrePixDAO consulta = new ConsultaPrePixDAO();
        String valida;
        valida = ValidadorCPF_CNPJ.validarCPF(request.getCpf_cnpj());
        try {
            if (valida == "Valor valido") {
                request.setCpf_cnpj(ValidadorCPF_CNPJ.limparCaracteresEspeciais(request.getCpf_cnpj()));
                String getToken = JwtGenerator.validaToken(token, request.getCpf_cnpj());
                if (getToken == "Token valido") {
                    cadastroReponse = consulta.validaCadastro(request);
                    if (cadastroReponse.getCodigo() == HttpStatus.OK) {
                        cpfJaEnviado = consulta.historico(request);
                        List<CpfJaEnviado> cpfsSemDuplicatas = validacaoRemocaoCPFDuplicado.removerCPFDuplicado(cpfJaEnviado);
                        if (cpfsSemDuplicatas != null) {
                            validacaoRemocaoCPFDuplicado.removerCPFDuplicado(cpfJaEnviado);
                            cadastroReponse.setCpfJaEnviado(cpfsSemDuplicatas);
                            //return cadastroReponse;
                            return ResponseEntity.status(HttpStatus.OK).body(cadastroReponse);
                        } else {
                            return ResponseEntity.status(HttpStatus.OK).body(cadastroReponse);
                        }
                    } else {
                        cadastroReponse.setMensagem("erro ao consultar historico");
                        cadastroReponse.getCodigo();
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(cadastroReponse);
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(obterRespostaErro(getToken, HttpStatus.UNAUTHORIZED));
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(obterRespostaErro(valida, HttpStatus.UNPROCESSABLE_ENTITY));
            }

        } catch (Exception e) {
            cadastroReponse.setMensagem("Serviço indisponível");
            cadastroReponse.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(cadastroReponse);

        }
    }


    @GetMapping("/consulta_destinatario")
    public ResponseEntity ConsultaDadosEnvio(@RequestHeader("Authorization") String token,
                                             ConsultaDestinatario request) {
        String valida;
        valida = ValidadorCPF_CNPJ.validarCPF(request.getCpf_cnpj());
        ResponseEntity<LoginResponse> response = null;
        ConsultaDestinatarioResponse cadastroReponse = new ConsultaDestinatarioResponse();
        ConsultaPrePixDAO consulta = new ConsultaPrePixDAO();
        try {
            if (valida == "Valor valido") {
                request.setCpf_cnpj(ValidadorCPF_CNPJ.limparCaracteresEspeciais(request.getCpf_cnpj()));
                String getToken = JwtGenerator.validaToken(token, request.getCpf_cnpj());
                if (getToken == "Token valido") {
                    String chavePix = ValidaChavePix.verificarTipoChavePix(request.getChavepix());
                    if (chavePix.equals(request.getTipoChavePix())) {
                        cadastroReponse = consulta.validaRemetente(request);
                        if (cadastroReponse.getMensagem() == "Usuario encontrado!") {

                            return ResponseEntity.status(HttpStatus.OK).body(cadastroReponse);
                        } else {
                            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(cadastroReponse);
                        }
                    } else {
                        cadastroReponse.setMensagem(chavePix);
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(cadastroReponse);
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(obterRespostaErro(getToken, HttpStatus.UNAUTHORIZED));
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(obterRespostaErro(valida, HttpStatus.UNPROCESSABLE_ENTITY));
            }
        } catch (Exception e) {
            cadastroReponse.setMensagem("Serviço indisponível");
            cadastroReponse.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(cadastroReponse);

        }


    }

    private ErroResponse obterRespostaErro(String msg, HttpStatus status) {
        ErroResponse response = new ErroResponse();
        response.setMensagem(msg);
        response.setCodigo(HttpStatus.valueOf(status.value()));
        return response;
    }

}

