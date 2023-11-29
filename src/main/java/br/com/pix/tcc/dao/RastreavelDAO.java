package br.com.pix.tcc.dao;

import br.com.pix.tcc.config.DatabaseConfig;
import br.com.pix.tcc.domain.Response.RastreavelConsultaResponse;
import br.com.pix.tcc.domain.Response.RastreavelResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RastreavelDAO {

    public static List<RastreavelResponse> consultaRastreavel(String cpf,String idTransferencia) {
        List<RastreavelResponse> responseList = new ArrayList<RastreavelResponse>();
        String sql = "SELECT * FROM rastreavel WHERE cpf_cnpj = '" + cpf + "' AND id_transferencia = '" + idTransferencia + "'";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Integer contador = 0;
        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            rst = pstm.executeQuery();
            while (rst.next()) {
                RastreavelResponse response = new RastreavelResponse(
                rst.getString("cpf_cnpj"),
                rst.getString("cpf_cnpj_destinatario"),
                rst.getString("localizacao_transferencia"),
                rst.getString("chave_pix_destinatario"),
                rst.getString("data_transferencia"),
                rst.getString("hora_transderencia"),
                rst.getString("tipo_transferencia"),
                rst.getString("id_transferencia")
                );
                responseList.add(response);
            }



        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
        return responseList;
    }

    public static List<RastreavelConsultaResponse> consultaTodosRastreavel() {
        List<RastreavelConsultaResponse> responseList = new ArrayList<>();
        String sql = "SELECT cpf_cnpj,id_transferencia FROM rastreavel";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rst = null;
        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            rst = pstm.executeQuery();
            while (rst.next()) {

                RastreavelConsultaResponse response = new RastreavelConsultaResponse(
                        rst.getString("cpf_cnpj"),
                        rst.getString("id_transferencia")
                );
                responseList.add(response);
            }
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return responseList;
    }

    public static List<RastreavelConsultaResponse> consultaTodosRastreavelDeUmCpf(String cpf) {
        List<RastreavelConsultaResponse> responseList = new ArrayList<>();
        String sql = "SELECT cpf_cnpj,id_transferencia FROM rastreavel WHERE cpf_cnpj LIKE '"+cpf+"%'";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rst = null;
        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            rst = pstm.executeQuery();
            while (rst.next()) {

                RastreavelConsultaResponse response = new RastreavelConsultaResponse(
                        rst.getString("cpf_cnpj"),
                        rst.getString("id_transferencia")
                );
                responseList.add(response);
            }
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return responseList;
    }


//    public static String AlteraSituaçãoRastreavel(String situacao) {
//        String sql = "UPDATE rastreavel set situacao = " + situacao + "";
//        Connection conn = null;
//        PreparedStatement pstm = null;
//        ResultSet rst = null;
//        try {
//            conn = DatabaseConfig.criaConexao();
//            pstm = (PreparedStatement) conn.prepareStatement(sql);
//            pstm.execute();
//            String resposta = "Dados atualizados";
//            return resposta;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            String resposta = "Erro ao atualizar dados";
//            return resposta;
//        }
//    }

    public static String DeleteSituaçãoRastreavel(String cpf,String idTransferencia) {
        String sql = "DELETE FROM rastreavel WHERE cpf_cnpj = '" + cpf + "' AND id_transferencia = '" + idTransferencia + "'";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rst = null;
        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.execute();
            String resposta = "Cpf: " + cpf + " excluido com sucesso";
            return resposta;

        } catch (Exception e) {
            e.printStackTrace();
            String resposta = "Erro ao excluir cpf";
            return resposta;
        }
    }

    public static String DeleteCpfRastreavel(String cpf) {
        String sql = "DELETE FROM rastreavel WHERE cpf_cnpj = '" + cpf + "";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rst = null;
        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.execute();
            String resposta = "Cpf: " + cpf + " excluido com sucesso";
            return resposta;

        } catch (Exception e) {
            e.printStackTrace();
            String resposta = "Erro ao excluir cpf";
            return resposta;
        }
    }
}

