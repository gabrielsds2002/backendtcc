package br.com.pix.tcc.dao;

import br.com.pix.tcc.config.DatabaseConfig;
import br.com.pix.tcc.domain.Response.ConsultaSaldoResponse;
import br.com.pix.tcc.domain.request.ConsultaSaldoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaSaldoDao {

    public static float consultaSaldo(int cpf) {
        String sql = "SELECT saldo FROM consulta_basica_cliente WHERE cpf_cnpj = '" + cpf + "'";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rst = null;
        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            rst = pstm.executeQuery();
            ConsultaSaldoResponse consulta = new ConsultaSaldoResponse();
            while (rst.next()) {
                consulta.setSaldo(rst.getInt("saldo"));
            }

            consulta.setMensagem("Sucesso ao recuperar saldo!!!");
            consulta.setCodigo(HttpStatus.OK);
            return consulta.getSaldo();
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }

    }
}
