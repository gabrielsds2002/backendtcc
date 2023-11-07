package br.com.pix.tcc.dao;

import br.com.pix.tcc.config.DatabaseConfig;
import br.com.pix.tcc.domain.Response.ConsultaSaldoResponse;
import br.com.pix.tcc.domain.Response.PixResponse;
import br.com.pix.tcc.domain.request.ConsultaSaldoRequest;
import br.com.pix.tcc.domain.request.PixRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;


@Slf4j
@Service
@RequiredArgsConstructor
public class TransferenciaDAO {


    public static String pixResponse(PixRequest pixRequest) {
        String sql = "INSERT INTO dados_rastreamento (cpf_cnpj, data_transferencia, hora_transderencia, localizacao_transferencia," +
                " tipo_transferencia, numero_conta_pagador, numero_conta_remetente, rastreavel) VALUES(?, ?, ?, ?, ?,?,?,?)";
        ResultSet rst = null;
        Connection conn = null;
        PreparedStatement pstm = null;


        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, pixRequest.getCpfRemetente());
            pstm.setString(2, LocalDate.now().toString());
            pstm.setString(3, LocalTime.now().toString());
            pstm.setString(4, pixRequest.getLocalizacaoTransferencia());
            pstm.setString(5, pixRequest.getTipoTransferencia());
            pstm.setString(6, pixRequest.getNumeroContaPagador());
            pstm.setString(7, pixRequest.getNumeroContaRemetente());
            pstm.setBoolean(8, pixRequest.getRastreavel());



            pstm.execute();
            String resposta = "Dados atualizados";
            return resposta;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String atualizaHistorico(PixRequest pixRequest) {
        String sql = "INSERT INTO historico (cpf_cnpj, cpf_cnpj_destinatario, nome_destinatario, chave_pix_destinatario)" +
                "VALUES(?, ?, ?, ?)";
        ResultSet rst = null;
        Connection conn = null;
        PreparedStatement pstm = null;


        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, pixRequest.getCpfRemetente());
            pstm.setString(2, pixRequest.getCpfDestinatario());
            pstm.setString(3, pixRequest.getNomeDestinatario());
            pstm.setString(4, pixRequest.getChavePixDestinatario());




            pstm.execute();
            String resposta = "Dados atualizados";
            return resposta;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String atualizaSaldo(PixRequest pixRequest) {
        String sql = "UPDATE consulta_basica_cliente SET saldo = saldo +"+ pixRequest.getValortransferencia()+"WHERE cpf_cnpj = "
                +pixRequest.getCpfDestinatario()+" AND numero_conta = +"+pixRequest.getNumeroContaRemetente()+"";
        ResultSet rst = null;
        Connection conn = null;
        PreparedStatement pstm = null;


        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, pixRequest.getCpfRemetente());
            pstm.setString(2, pixRequest.getCpfDestinatario());
            pstm.setString(3, pixRequest.getNomeDestinatario());
            pstm.setString(4, pixRequest.getChavePixDestinatario());




            pstm.execute();
            String resposta = "Dados atualizados";
            return resposta;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void atualizarSaldoSoma(String cpf, String numeroConta, Float valorParaAdicionar) {

            String sql = "UPDATE consulta_basica_cliente SET saldo = saldo + ? WHERE cpf_cnpj = ? AND numero_conta = ?";

            ResultSet rst = null;
            Connection conn = null;
            PreparedStatement pstm = null;
            try  {
                conn = DatabaseConfig.criaConexao();
                pstm = (PreparedStatement) conn.prepareStatement(sql);
                pstm.setFloat(1, valorParaAdicionar);
                pstm.setString(2, cpf);
                pstm.setString(3, numeroConta);
                pstm.execute();


            }
         catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void atualizarSaldo(String cpf, String numeroConta, Float valorParaAdicionar) {

        String sql = "UPDATE consulta_basica_cliente SET saldo = saldo - ? WHERE cpf_cnpj = ? AND numero_conta = ?";

        ResultSet rst = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        try  {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setFloat(1, valorParaAdicionar);
            pstm.setString(2, cpf);
            pstm.setString(3, numeroConta);
            pstm.execute();


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }






}
