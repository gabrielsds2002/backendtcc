package br.com.pix.tcc.dao;

import br.com.pix.tcc.business.Formata;
import br.com.pix.tcc.config.DatabaseConfig;
import br.com.pix.tcc.domain.DadosDestinatario;
import br.com.pix.tcc.domain.request.PixRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;


@Slf4j
@Service
@RequiredArgsConstructor
public class TransferenciaDAO {


    static Formata formata;

    public static String pixResponse(PixRequest pixRequest, Boolean rastreavel,Integer numContPag,Integer numContRem, String id) {
        String sql = "INSERT INTO dados_rastreamento (cpf_cnpj, data_transferencia, hora_transderencia, localizacao_transferencia," +
                " tipo_transferencia, numero_conta_pagador, numero_conta_remetente, rastreavel, id_transferencia) VALUES(?, ?, ?, ?, ?,?,?,?,?)";
        ResultSet rst = null;
        Connection conn = null;
        PreparedStatement pstm = null;

        if (rastreavel == null){
            rastreavel =false;
        }



        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, pixRequest.getCpf_remetente());
            pstm.setString(2, formata.formatarLocalDate(LocalDate.now()));
            pstm.setString(3, formata.formatarLocalTime(LocalTime.now()));
            pstm.setString(4, pixRequest.getLocalizacao_ransferencia());
            pstm.setString(5, "PIX");
            pstm.setInt(6, numContPag);
            pstm.setInt(7, numContRem);
            pstm.setBoolean(8, rastreavel);
            pstm.setString(9, id);



            pstm.execute();
            String resposta = "Dados atualizados";
            return resposta;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String atualizaHistorico(PixRequest pixRequest, DadosDestinatario dadosDestinatario) {
        String sql = "INSERT INTO historico (cpf_cnpj, cpf_cnpj_destinatario, nome_destinatario, chave_pix_destinatario,banco_destinatario)" +
                "VALUES(?, ?, ?, ?,?)";
        ResultSet rst = null;
        Connection conn = null;
        PreparedStatement pstm = null;


        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, pixRequest.getCpf_remetente());
            pstm.setString(2, dadosDestinatario.getCpf_destinatario());
            pstm.setString(3, dadosDestinatario.getNome_destinatario());
            pstm.setString(4, pixRequest.getChave_pix_destinatario());
            pstm.setString(5, dadosDestinatario.getBanco_destinatario());




            pstm.execute();
            String resposta = "Dados atualizados";
            return resposta;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void atualizarSaldoSoma(String cpf, Float valorParaAdicionar) {

            String sql = "UPDATE consulta_basica_cliente SET saldo = saldo + ? WHERE cpf_cnpj = ?";

            ResultSet rst = null;
            Connection conn = null;
            PreparedStatement pstm = null;
            try  {
                conn = DatabaseConfig.criaConexao();
                pstm = (PreparedStatement) conn.prepareStatement(sql);
                pstm.setFloat(1, valorParaAdicionar);
                pstm.setString(2, cpf);
                pstm.execute();


            }
         catch (Exception e) {
            e.printStackTrace();
             System.out.println("Erro");
             throw new RuntimeException(e);
        }
    }


    public static void atualizarSaldo(String cpf, Float valorParaAdicionar) {

        String sql = "UPDATE consulta_basica_cliente SET saldo = saldo - ? WHERE cpf_cnpj = ?";

        ResultSet rst = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        try  {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setFloat(1, valorParaAdicionar);
            pstm.setString(2, cpf);
            pstm.execute();


        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }








}
