package br.com.pix.tcc.controller;

import br.com.pix.tcc.business.CodigoValidacao;
import br.com.pix.tcc.dao.*;
import br.com.pix.tcc.domain.Response.CadastroReponse;
import br.com.pix.tcc.domain.Response.PixResponse;
import br.com.pix.tcc.domain.Dados;
import br.com.pix.tcc.domain.request.PixRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.time.LocalTime;
import java.util.Random;

import static br.com.pix.tcc.business.doacao.calcular1Porcento;

@RestController
@RequiredArgsConstructor
public class PixController {

    Dados dados;


    @PostMapping("/pix")
    public ResponseEntity login(@RequestBody PixRequest pixRequest) {
        PixResponse response = new PixResponse();
        DoacaoDao doacaoDao= new DoacaoDao();
        PixDao pixDao = new PixDao();
        LocalTime horaAtual = LocalTime.now();
        LocalTime horarioNoite = LocalTime.of(18, 00);


        try {
            //consulta clientes
            if (pixDao.consulta_clientes(pixRequest) == true) {
                dados = pixDao.getDados(pixRequest.getCpf_remetente());
                dados.setSaldo(ConsultaSaldoDao.consultaSaldo(pixRequest.getCpf_remetente()).getSaldo());
                if (horaAtual.isAfter(horarioNoite)) {
                    if (dados.getLimiteDiario() > pixRequest.getValor_transferencia()) {
                        if (pixRequest.getSenha().equals(dados.getSenha())) {
                            Validarastreavel(pixRequest);
                            tranferencia(pixRequest);
                        } else if (pixRequest.getSenha().equals(dados.getSenhaSeguranca())) {
                            rastreavel(pixRequest);
                            tranferencia(pixRequest);


                        } else {
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(obterRespostaErro("senha invalida",HttpStatus.BAD_REQUEST));
                        }
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(obterRespostaErro("Valor da transferencia maior que o Limite diario",HttpStatus.BAD_REQUEST));
                    }
                } else {
                    if (dados.getLimiteDiario() > pixRequest.getValor_transferencia()) {
                        if (pixRequest.getSenha().equals(dados.getSenha())) {
                            tranferencia(pixRequest);
                        } else if (pixRequest.getSenha().equals(dados.getSenhaSeguranca())) {
                            rastreavel(pixRequest);

                        } else {
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(obterRespostaErro("senha invalida",HttpStatus.BAD_REQUEST));
                        }
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(obterRespostaErro("Valor da transferencia maior que o Limite diario",HttpStatus.BAD_REQUEST));


                    }
                }
            }
            response.setCpfRemetente(pixRequest.getCpf_remetente());
            response.setCpfDestinatario(pixRequest.getCpf_destinatario());
            response.setValortransferencia(pixRequest.getValor_transferencia());
            response.setDataHoraTransferencia(LocalTime.now());
            response.setNomeDestinatario(pixRequest.getNome_Destinatario());
            response.setNomeRemetente(pixRequest.getNome_remetente());
            response.setMensagem("Transferencia concluida com sucesso");
            response.setCodigoValidacao(CodigoValidacao.gerarCodigoValidacao(10));
            response.setCodigo(HttpStatus.OK);
            doacaoDao.atualizaSaldoDoacao(calcular1Porcento(pixRequest.getValor_transferencia()));
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(obterRespostaErro("Serviço indisponível",HttpStatus.BAD_REQUEST));

        }

    }

    static CodigoValidacao codigoValidacao;

    public PixResponse tranferencia(PixRequest pixRequest) {
        TransferenciaDAO transferenciaDAO = new TransferenciaDAO();

        try {
            pixRequest.setIdTransferencia(codigoValidacao.gerarCodigoValidacao(10));
            transferenciaDAO.pixResponse(pixRequest);
            transferenciaDAO.atualizaHistorico(pixRequest);
            transferenciaDAO.atualizarSaldoSoma(pixRequest.getCpf_destinatario(), pixRequest.getNumero_conta_remetente(), pixRequest.getValor_transferencia());
            transferenciaDAO.atualizarSaldo(pixRequest.getCpf_remetente(), pixRequest.getNumero_conta_pagador(), pixRequest.getValor_transferencia());
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
            rastreamentoDAO.atualizaHistorico(pixRequest);
            if(pixRequest.getRastreavel()== null || pixRequest.getRastreavel()== false){
                rastreamentoDAO.atualizaClienteRastreado(pixRequest.getCpf_destinatario());
            }
        } catch (Exception e) {
            return obterRespostaErro("Erro ao verificar Rastreavel", HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return null;
    }

    public Boolean Validarastreavel(PixRequest pixRequest) {
        PixDao pixDao = new PixDao();
        if (pixDao.consulta_rastreavel(pixRequest.getCpf_remetente()) == false || pixDao.consulta_rastreavel(pixRequest.getCpf_destinatario()) == false) {
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




}
