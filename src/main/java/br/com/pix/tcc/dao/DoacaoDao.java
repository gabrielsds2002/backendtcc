package br.com.pix.tcc.dao;

import br.com.pix.tcc.config.DatabaseConfig;
import br.com.pix.tcc.domain.Response.AtualizaMesResponse;
import br.com.pix.tcc.domain.Response.ConsultaResponse;
import br.com.pix.tcc.domain.Response.DoacaoResponse;
import br.com.pix.tcc.domain.Response.RastreavelConsultaResponse;
import br.com.pix.tcc.domain.request.AtualizaMesRequest;
import br.com.pix.tcc.domain.request.PixRequest;
import org.springframework.http.HttpStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DoacaoDao {

    public static String atualizaSaldoDoacao(float doacao) {
        String sql = "UPDATE doacao SET saldo = saldo +"+ doacao +"";
        ResultSet rst = null;
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.execute();
            String resposta = "Dados atualizados";
            return resposta;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public static AtualizaMesResponse atualizaSaldoDoacaoMes(AtualizaMesRequest atualizaMesRequest) {
        AtualizaMesResponse atualizaMesResponse =new AtualizaMesResponse();
        String sql = "INSERT INTO tcc.doacaoMes (saldo, mes, ano) VALUES (" + atualizaMesRequest.getSaldo() + ", '"
                + atualizaMesRequest.getMes() + "', '" + atualizaMesRequest.getAno() + "')";
        ResultSet rst = null;
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.execute();
            String resposta = "Dados atualizados";
            atualizaMesResponse.setMensagem(resposta);
            atualizaMesResponse.setCodigo(HttpStatus.OK);
            return atualizaMesResponse;

        } catch (Exception e) {
            String resposta = "Não foi possivel atualizar os dados";
            atualizaMesResponse.setMensagem(resposta);
            atualizaMesResponse.setCodigo(HttpStatus.BAD_REQUEST);
            return atualizaMesResponse;
        }
    }


    public static List<ConsultaResponse> consultaDoacaoAll() {
        List<ConsultaResponse> responseList = new ArrayList<>();
        String sql = "SELECT * FROM tcc.doacaoMes";
        ResultSet rst = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        DoacaoResponse doacaoResponse = new DoacaoResponse();

        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.execute();
            while (rst.next()) {

                ConsultaResponse consultaResponse = new ConsultaResponse(
                        rst.getFloat("saldo"),
                        rst.getString("mes"),
                        rst.getString("ano")
                );
                responseList.add(consultaResponse);
            }
            return responseList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static DoacaoResponse consultaSaldoDoacaoMes() {

        String sql = "SELECT * FROM tcc.doacao";
        ResultSet rst = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        DoacaoResponse doacaoResponse = new DoacaoResponse();

        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.execute();
            doacaoResponse.setSaldo(rst.getFloat("cpf_cnpj"));
            return doacaoResponse;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
