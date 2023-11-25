package br.com.pix.tcc.dao;

import br.com.pix.tcc.config.DatabaseConfig;
import br.com.pix.tcc.domain.request.PixRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;

public class RastreamentoDAO {



    public static String atualizaHistorico(PixRequest pixRequest) {
        String sql = "INSERT INTO rastreavel (cpf_cnpj, cpf_cnpj_destinatario, localizacao_transferencia, chave_pix_destinatario, data_transferencia, hora_transderencia, tipo_transferencia, id_transferencia)" +
                "VALUES(?,?,?,?,?,?,?,?)";
        ResultSet rst = null;
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, pixRequest.getCpf_remetente());
            pstm.setString(2, pixRequest.getCpf_destinatario());
            pstm.setString(3, pixRequest.getLocalizacao_ransferencia());
            pstm.setString(4, pixRequest.getChave_pix_destinatario());
            pstm.setString(5, LocalDate.now().toString());
            pstm.setString(6, LocalTime.now().toString());
            pstm.setString(7, pixRequest.getTipo_transferencia());
            pstm.setString(8, pixRequest.getIdTransferencia());


            System.out.println("Teste");
            pstm.execute();
            String resposta = "Dados atualizados";
            return resposta;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String atualizaClienteRastreado(String cpf) {
        String sql = "UPDATE consulta_basica_cliente SET rastreavel = true WHERE cpf_cnpj = ?";

        ResultSet rst = null;
        Connection conn = null;
        PreparedStatement pstm = null;


        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, String.valueOf(cpf));
            pstm.execute();
            String resposta = "Dados atualizados";
            return resposta;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public static String consultaRastreavel(PixRequest pixRequest) {
        String sql = "select * INTO rastreavel (cpf_cnpj, cpf_cnpj_destinatario, localizacao_transferencia, chave_pix_destinatario, data_transferencia, hora_transderencia, tipo_transferencia)" +
                "VALUES(?,?,?,?,?,?,?)";
        ResultSet rst = null;
        Connection conn = null;
        PreparedStatement pstm = null;


        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, pixRequest.getCpf_remetente());
            pstm.setString(2, pixRequest.getCpf_destinatario());
            pstm.setString(3, pixRequest.getLocalizacao_ransferencia());
            pstm.setString(4, pixRequest.getChave_pix_destinatario());
            pstm.setString(5, LocalDate.now().toString());
            pstm.setString(6, LocalTime.now().toString());
            pstm.setString(7, pixRequest.getTipo_transferencia());





            pstm.execute();
            String resposta = "Dados atualizados";
            return resposta;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String atualizaSaldo(PixRequest pixRequest) {
        String sql = "UPDATE consulta_basica_cliente SET saldo = saldo +"+ pixRequest.getValor_transferencia()+"WHERE cpf_cnpj = "
                +pixRequest.getCpf_destinatario()+" AND numero_conta = +"+pixRequest.getNumero_conta_remetente()+"";
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




            pstm.execute();
            String resposta = "Dados atualizados";
            return resposta;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
