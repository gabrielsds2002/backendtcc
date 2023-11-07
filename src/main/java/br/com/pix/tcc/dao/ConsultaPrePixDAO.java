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


    //consulta cliente(saldo,chavepix, limiteDiario,limiteNoturno,banco,nome)

    public ConsultaPrePixResponse validaCadastro(ConsultaPrePixRequest cadastro) {

        String sql = "SELECT nome,saldo,chave_pix,limite_diario,limite_noturno,instituicao_financeira FROM consulta_basica_cliente WHERE cpf_cnpj = '" + cadastro.getCpf_cnpj() + "'";


        List<CadastroRequest> cadastros = new ArrayList<CadastroRequest>();

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rst = null;

        try {

            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            rst = pstm.executeQuery();
            ConsultaPrePixResponse consulta = new ConsultaPrePixResponse();

            while (rst.next()) {


                consulta.setNome(rst.getString("nome"));
                consulta.setSaldo(rst.getInt("saldo"));
                consulta.setChave_pix(rst.getString("chave_pix"));
                consulta.setLimite_diario(rst.getInt("limite_diario"));
                consulta.setLimite_noturno(rst.getInt("limite_noturno"));
                consulta.setInstituicao_financeira(rst.getString("instituicao_financeira"));



            }

            return consulta;


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public ConsultaPrePixResponse historico(ConsultaPrePixRequest cadastro) {

        String sql = "SELECT cpf_cnpj FROM historico WHERE cpf_cnpj = '" + cadastro.getCpf_cnpj() + "'";


        List<CadastroRequest> cadastros = new ArrayList<CadastroRequest>();

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Integer contador =0;
        try {

            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            rst = pstm.executeQuery();
            ConsultaPrePixResponse consulta = new ConsultaPrePixResponse();
            List<CpfJaEnviado> cpfJaEnviado = new ArrayList<CpfJaEnviado>();
            CpfJaEnviado cpf = new CpfJaEnviado();

            while (rst.next()) {

                cpf.setChave_pix(rst.getString("cpf_cnpj_destinatario"));
                cpf.setNome(rst.getString("nome_destinatario"));
                cpf.setCpf_cnpj(rst.getInt("chave_pix_destinatario"));
                cpfJaEnviado.add(contador,cpf);
                contador++;
            }
            return consulta;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //consultar historico de transferencia se tiver consultar
        //consulta cliente(chavepix,banco,nome)



}
