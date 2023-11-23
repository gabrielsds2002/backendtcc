package br.com.pix.tcc.dao;

import br.com.pix.tcc.config.DatabaseConfig;
import br.com.pix.tcc.domain.CpfJaEnviado;
import br.com.pix.tcc.domain.Response.ConsultaPrePixResponse;
import br.com.pix.tcc.domain.request.CadastroRequest;
import br.com.pix.tcc.domain.request.ConsultaPrePixRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaPrePixDAO {


    public ConsultaPrePixResponse validaCadastro(ConsultaPrePixRequest cadastro) {

        String sql = "SELECT nome,saldo,chave_pix,limite_diario,limite_noturno,instituicao_financeira,numero_conta FROM consulta_basica_cliente WHERE cpf_cnpj = '" + cadastro.getCpf_cnpj() + "'";


        List<CadastroRequest> cadastros = new ArrayList<CadastroRequest>();

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rst = null;
        ConsultaPrePixResponse consulta = new ConsultaPrePixResponse();
        try {

            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            rst = pstm.executeQuery();


            while (rst.next()) {
                consulta.setNome(rst.getString("nome"));
                consulta.setSaldo(rst.getInt("saldo"));
                consulta.setNome(rst.getString("chave_pix"));
                consulta.setLimite_diario(rst.getInt("limite_diario"));
                consulta.setLimite_noturno(rst.getInt("limite_noturno"));
                consulta.setInstituicao_financeira(rst.getString("instituicao_financeira"));
                consulta.setNumeroContaRemetente(rst.getString("numero_conta"));
            }

            return consulta;


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public List<CpfJaEnviado> historico(ConsultaPrePixRequest cadastro) {
        List<CpfJaEnviado> cpfJaEnviado = new ArrayList<CpfJaEnviado>();
        String sql = "SELECT cpf_cnpj_destinatario,nome_destinatario,chave_pix_destinatario FROM historico WHERE cpf_cnpj = '" + cadastro.getCpf_cnpj() + "'";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rst = null;
        try {

            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            rst = pstm.executeQuery();
            while (rst.next()) {
                CpfJaEnviado response = new CpfJaEnviado(
                        rst.getInt("cpf_cnpj_destinatario"),
                        rst.getString("nome_destinatario"),
                        rst.getString("chave_pix_destinatario")
                );
                cpfJaEnviado.add(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return cpfJaEnviado;
    }
}