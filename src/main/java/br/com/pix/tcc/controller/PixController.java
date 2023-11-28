package br.com.pix.tcc.controller;

import br.com.pix.tcc.business.CodigoValidacao;
import br.com.pix.tcc.business.Formata;
import br.com.pix.tcc.business.ValidadorCPF_CNPJ;
import br.com.pix.tcc.config.JwtGenerator;
import br.com.pix.tcc.dao.*;
import br.com.pix.tcc.domain.DadosDestinatario;
import br.com.pix.tcc.domain.Response.CadastroReponse;
import br.com.pix.tcc.domain.Response.ErroResponse;
import br.com.pix.tcc.domain.Response.PixResponse;
import br.com.pix.tcc.domain.Dados;
import br.com.pix.tcc.domain.request.PixRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

import static br.com.pix.tcc.business.doacao.calcular1Porcento;

@RestController
@RequiredArgsConstructor
public class PixController {

    Dados dados;
    DadosDestinatario dadosDestinatario;

    Formata formata;

    ValidadorCPF_CNPJ validadorCPFCnpj;

    String idTransferenvia =codigoValidacao.gerarCodigoValidacao(10);


    @PostMapping("/pix")
    public ResponseEntity login(@RequestHeader("Authorization") String token,
                                @RequestBody PixRequest pixRequest) {
        PixResponse response = new PixResponse();
        DoacaoDao doacaoDao = new DoacaoDao();
        PixDao pixDao = new PixDao();
        LocalTime horaAtual = LocalTime.now();
        LocalTime horarioNoite = LocalTime.of(18, 00);



        try {
            String valida;
            valida = ValidadorCPF_CNPJ.validarCPF(pixRequest.getCpf_remetente());
            if (valida == "Valor valido") {

                if (pixRequest.getChave_pix_destinatario() == null || pixRequest.getChave_pix_destinatario().isEmpty()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(obterRespostaErro("Chave Pix Destinatario está vazia", HttpStatus.FORBIDDEN));
                } else if (pixRequest.getCpf_remetente() == null || pixRequest.getCpf_remetente().isEmpty()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(obterRespostaErro("Chave Pix Remetente está vazia", HttpStatus.FORBIDDEN));
                } else {

                    pixRequest.setChave_pix_destinatario(validadorCPFCnpj.limparCaracteresEspeciais(pixRequest.getChave_pix_destinatario()));
                    pixRequest.setCpf_remetente(validadorCPFCnpj.limparCaracteresEspeciais(pixRequest.getCpf_remetente()));
                    pixRequest.setCpf_remetente(ValidadorCPF_CNPJ.limparCaracteresEspeciais(pixRequest.getCpf_remetente()));
                    String getToken = JwtGenerator.validaToken(token, pixRequest.getCpf_remetente());
                    if (getToken == "Token valido") {
                        dados = pixDao.getDados(pixRequest.getCpf_remetente());
                        dadosDestinatario = pixDao.getDadosDestinatario(pixRequest.getChave_pix_destinatario());
                        if (pixDao.consulta_clientes(pixRequest,dadosDestinatario.getCpf_destinatario()) == true) {
                            if (horaAtual.isAfter(horarioNoite)) {
                                if (dados.getLimiteDiario() > pixRequest.getValor_transferencia()) {
                                    if (pixRequest.getSenha().equals(dados.getSenha())) {
                                        Validarastreavel(pixRequest);
                                        tranferencia(pixRequest);
                                    } else if (pixRequest.getSenha().equals(dados.getSenhaSeguranca())) {
                                        rastreavel(pixRequest);
                                        tranferencia(pixRequest);


                                    } else {
                                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(obterRespostaErroRes("senha invalida", HttpStatus.BAD_REQUEST));
                                    }
                                } else {
                                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(obterRespostaErroRes("Valor da transferencia maior que o Limite diario", HttpStatus.BAD_REQUEST));
                                }
                            } else {
                                if (dados.getLimiteDiario() > pixRequest.getValor_transferencia()) {
                                    if (pixRequest.getSenha().equals(dados.getSenha())) {
                                        tranferencia(pixRequest);
                                    } else if (pixRequest.getSenha().equals(dados.getSenhaSeguranca())) {
                                        rastreavel(pixRequest);

                                    } else {
                                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(obterRespostaErroRes("senha invalida", HttpStatus.BAD_REQUEST));
                                    }
                                } else {
                                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(obterRespostaErroRes("Valor da transferencia maior que o Limite diario", HttpStatus.BAD_REQUEST));


                                }
                            }
                        }
                        response.setCpfRemetente(formata.formatarCPF(pixRequest.getCpf_remetente()));
                        response.setCpfDestinatario(formata.formatarCPF(dadosDestinatario.getCpf_destinatario()));
                        response.setValortransferencia(pixRequest.getValor_transferencia());
                        response.setHoraTransferencia(formata.formatarLocalTime(LocalTime.now()));
                        response.setDataTransferencia(formata.formatarLocalDate(LocalDate.now()));
                        response.setNomeDestinatario(dadosDestinatario.getNome_destinatario());
                        response.setNomeRemetente(dados.getNome());
                        response.setMensagem("Transferencia concluida com sucesso");
                        response.setCodigoValidacao(CodigoValidacao.gerarCodigoValidacao(10));
                        response.setCodigo(HttpStatus.OK);
                        doacaoDao.atualizaSaldoDoacao(calcular1Porcento(pixRequest.getValor_transferencia()));
                        return ResponseEntity.ok(response);
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(obterRespostaErroRes(getToken, HttpStatus.UNAUTHORIZED));
                    }
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(obterRespostaErro(valida, HttpStatus.UNPROCESSABLE_ENTITY));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(obterRespostaErroRes("Serviço indisponível", HttpStatus.BAD_REQUEST));

        }

    }

    static CodigoValidacao codigoValidacao;

    public PixResponse tranferencia(PixRequest pixRequest) {
        TransferenciaDAO transferenciaDAO = new TransferenciaDAO();

        try {

            transferenciaDAO.pixResponse(pixRequest,dados.getRastreavel(),dados.getNumero_conta_pagador(),dadosDestinatario.getNumero_conta_destinatario(),idTransferenvia);
            transferenciaDAO.atualizaHistorico(pixRequest,dadosDestinatario);
            transferenciaDAO.atualizarSaldoSoma(dadosDestinatario.getCpf_destinatario(), pixRequest.getValor_transferencia());
            transferenciaDAO.atualizarSaldo(pixRequest.getCpf_remetente(), pixRequest.getValor_transferencia());
            PixResponse response = new PixResponse();
            response.setCodigo(HttpStatus.OK);
            return response;
        } catch (Exception e) {
            return obterRespostaErro("Erro ao executar transferencia", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    public PixResponse rastreavel(PixRequest pixRequest) {
        try {
            RastreamentoDAO rastreamentoDAO = new RastreamentoDAO();
            tranferencia(pixRequest);

            rastreamentoDAO.atualizaHistorico(pixRequest,dadosDestinatario.getCpf_destinatario(),idTransferenvia);
            if (dados.getRastreavel() == null || dados.getRastreavel() == false) {
                rastreamentoDAO.atualizaClienteRastreado(dadosDestinatario.getCpf_destinatario());
            }
        } catch (Exception e) {
            return obterRespostaErro("Erro ao verificar Rastreavel", HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return null;
    }

    public Boolean Validarastreavel(PixRequest pixRequest) {
        PixDao pixDao = new PixDao();
        if (pixDao.consulta_rastreavel(pixRequest.getCpf_remetente()) == false || pixDao.consulta_rastreavel(dadosDestinatario.getCpf_destinatario()) == false) {
            tranferencia(pixRequest);
            return false;
        } else {
            rastreavel(pixRequest);
            return true;
        }
    }

    private PixResponse obterRespostaErro(String msg, HttpStatus status) {
        PixResponse response;
        PixResponse obterResposta = new PixResponse();
        obterResposta.setMensagem(msg);
        obterResposta.setCodigo(HttpStatus.valueOf(status.value()));
        response = obterResposta;
        return response;
    }

    private ErroResponse obterRespostaErroRes(String msg, HttpStatus status) {
        ErroResponse response = new ErroResponse();
        response.setMensagem(msg);
        response.setCodigo(HttpStatus.valueOf(status.value()));
        return response;
    }


}
