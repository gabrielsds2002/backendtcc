package br.com.pix.tcc.dao;

import br.com.pix.tcc.business.Formata;
import br.com.pix.tcc.config.DatabaseConfig;
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

    public static String pixResponse(PixRequest pixRequest) {
        String sql = "INSERT INTO dados_rastreamento (cpf_cnpj, data_transferencia, hora_transderencia, localizacao_transferencia," +
                " tipo_transferencia, numero_conta_pagador, numero_conta_remetente, rastreavel, id_transferencia) VALUES(?, ?, ?, ?, ?,?,?,?,?)";
        ResultSet rst = null;
        Connection conn = null;
        PreparedStatement pstm = null;

        if (pixRequest.getRastreavel()==null){
            pixRequest.setRastreavel(false);
        }
        System.out.println("Teste:    "+pixRequest.getIdTransferencia());


        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, pixRequest.getCpf_remetente());
            pstm.setString(2, formata.formatarLocalDate(LocalDate.now()));
            pstm.setString(3, formata.formatarLocalTime(LocalTime.now()));
            pstm.setString(4, pixRequest.getLocalizacao_ransferencia());
            pstm.setString(5, pixRequest.getTipo_transferencia());
            pstm.setInt(6, pixRequest.getNumero_conta_pagador());
            pstm.setInt(7, pixRequest.getNumero_conta_remetente());
            pstm.setBoolean(8, pixRequest.getRastreavel());
            pstm.setString(9, pixRequest.getIdTransferencia());



            pstm.execute();
            String resposta = "Dados atualizados";
            return resposta;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String atualizaHistorico(PixRequest pixRequest) {
        String sql = "INSERT INTO historico (cpf_cnpj, cpf_cnpj_destinatario, nome_destinatario, chave_pix_destinatario,banco_destinatario)" +
                "VALUES(?, ?, ?, ?,?)";
        ResultSet rst = null;
        Connection conn = null;
        PreparedStatement pstm = null;


        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, pixRequest.getCpf_remetente());
            pstm.setString(2, pixRequest.getCpf_destinatario());
            pstm.setString(3, pixRequest.getNome_Destinatario());
            pstm.setString(4, pixRequest.getChave_pix_destinatario());
            pstm.setString(5, pixRequest.getBanco_destinatario());




            pstm.execute();
            String resposta = "Dados atualizados";
            return resposta;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void atualizarSaldoSoma(String cpf, int numeroConta, Float valorParaAdicionar) {

            String sql = "UPDATE consulta_basica_cliente SET saldo = saldo + ? WHERE cpf_cnpj = ? AND numero_conta = ?";

            ResultSet rst = null;
            Connection conn = null;
            PreparedStatement pstm = null;
            try  {
                conn = DatabaseConfig.criaConexao();
                pstm = (PreparedStatement) conn.prepareStatement(sql);
                pstm.setFloat(1, valorParaAdicionar);
                pstm.setString(2, cpf);
                pstm.setInt(3, numeroConta);
                pstm.execute();


            }
         catch (Exception e) {
            e.printStackTrace();
             System.out.println("Erro");
             throw new RuntimeException(e);
        }
    }


    public static void atualizarSaldo(String cpf, int numeroConta, Float valorParaAdicionar) {

        String sql = "UPDATE consulta_basica_cliente SET saldo = saldo - ? WHERE cpf_cnpj = ? AND numero_conta = ?";

        ResultSet rst = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        try  {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setFloat(1, valorParaAdicionar);
            pstm.setString(2, cpf);
            pstm.setInt(3, numeroConta);
            pstm.execute();


        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }








}
