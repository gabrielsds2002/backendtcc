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

    public static ConsultaSaldoResponse consultaSaldo(String cpf) {
        String sql = "SELECT saldo,cpf_cnpj FROM consulta_basica_cliente WHERE cpf_cnpj = '" + cpf + "'";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rst = null;
        ConsultaSaldoResponse consulta = new ConsultaSaldoResponse();
        try {
            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            rst = pstm.executeQuery();

            while (rst.next()) {
                consulta.setSaldo(rst.getInt("saldo"));
                consulta.setCpf_cnpj(rst.getString("cpf_cnpj"));
            }
            if (consulta.getCpf_cnpj() != null) {
                consulta.setMensagem("Sucesso ao recuperar saldo!!!");
                consulta.setCodigo(HttpStatus.OK);
                return consulta;
            }else {
                consulta.setMensagem("Falha ao recuperar saldo!!!");
                consulta.setCodigo(HttpStatus.BAD_REQUEST);
                return consulta;
            }
        } catch (Exception e) {
            consulta.setMensagem("Falha ao recuperar saldo!!!");
            consulta.setCodigo(HttpStatus.BAD_REQUEST);
            return consulta;
        }

    }
}
