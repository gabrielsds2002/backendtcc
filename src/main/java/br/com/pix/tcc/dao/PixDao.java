package br.com.pix.tcc.dao;

import br.com.pix.tcc.business.Cript;
import br.com.pix.tcc.config.DatabaseConfig;
import br.com.pix.tcc.domain.Dados;
import br.com.pix.tcc.domain.request.CadastroRequest;
import br.com.pix.tcc.domain.request.PixRequest;
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
public class PixDao {

    Cript cript;

    public Boolean consulta_clientes(PixRequest pixRequest) {

        String sql = "SELECT * FROM consulta_basica_cliente WHERE cpf_cnpj ='" + pixRequest.getCpfRemetente() + "' OR cpf_cnpj ='"+ pixRequest.getCpfDestinatario() + "'";

       PixRequest request = new PixRequest();

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Integer contador =0;

        try {

            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            rst = pstm.executeQuery();
            CadastroRequest consulta = new CadastroRequest();

            while (rst.next()) {
                consulta.setCpf_cnpj(rst.getInt("cpf_cnpj"));
                consulta.setNome(rst.getString("senha_app"));
                contador++;
            }
            System.out.println(consulta.getCpf_cnpj());
            if (consulta.getCpf_cnpj() != null) {
                return false;
            } else {
                return true;

            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Dados getDados(String cpf){

        String sql = "SELECT senha,senha_seguranca FROM consulta_basica_cliente WHERE cpf_cnpj ='"+ cpf +"'";

        List<CadastroRequest> cadastros = new ArrayList<CadastroRequest>();

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Dados senhas = new Dados();

        try {

            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            rst = pstm.executeQuery();
            CadastroRequest consulta = new CadastroRequest();
            String senha = null;
            String senha2 = null;

            while (rst.next()) {
                senhas.setSenha(cript.decryptIntFromBase64(rst.getString("senha")));
                senhas.setSenhaSeguranca(cript.decryptIntFromBase64(rst.getString("senha_seguranca")));
            }
        if(senhas.getSenha() == null || senhas.getSenhaSeguranca() == null || senhas.getSenha()  == "" || senhas.getSenhaSeguranca() == ""){
             senhas.setErro("Não foi possivel recuperar as senhas");
             return senhas;

        } else {

            return senhas;
        }


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Boolean consulta_rastreavel(String cpf) {

        String sql = "SELECT rastreavel FROM consulta_basica_cliente WHERE cpf_cnpj ='"+ cpf +"'";

        List<CadastroRequest> cadastros = new ArrayList<CadastroRequest>();

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Integer contador =0;
        String rastreavel = null;

        try {

            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            rst = pstm.executeQuery();
            CadastroRequest consulta = new CadastroRequest();

            while (rst.next()) {
                rastreavel =rst.getString("rastreavel");
            }

            if (rastreavel == "false" || rastreavel == "") {
                return false;
            } else {
                return true;

            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//    public String pix(PixRequest pixRequest) {
//        String sql = "INSERT INTO consulta_basica_cliente( nome, sobrenome, saldo, rastreavel, lougradouro, numero, " +
//                "complemento, bairro, cidade, estado, pais, estado_civil, idade, cpf_cnpj, data_nac_criacao, cep, " +
//                "chave_pix, limite_diario, limite_noturno, senha, senha_seguranca, instituicao_financeira, numero_conta, " +
//                "email, tipo_chave_pix, sexo, telefone, nacionalidade, senha_app) " +
//                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//        Connection conn = null;
//        PreparedStatement pstm = null;
//
//
//        try {
//            conn = DatabaseConfig.criaConexao();
//            pstm = (PreparedStatement) conn.prepareStatement(sql);
//            pstm.setString(1, cadastro.getNome());
//            pstm.setString(2, cadastro.getSobrenome());
//            pstm.setFloat(3, cadastro.getSaldo());
//            pstm.setString(4, "");
//            pstm.setString(5, cadastro.getLougradouro());
//            pstm.setInt(6, cadastro.getNumero());
//            pstm.setString(7, cadastro.getComplemento());
//            pstm.setString(8, cadastro.getBairro());
//            pstm.setString(9, cadastro.getCidade());
//            pstm.setString(10, cadastro.getEstado());
//            pstm.setString(11, cadastro.getPais());
//            pstm.setString(12, cadastro.getEstado_civil());
//            pstm.setInt(13, cadastro.getIdade());
//            pstm.setInt(14,  cadastro.getCpf_cnpj());
//            pstm.setString(15, cadastro.getData_nac_criacao());
//            pstm.setString(16, cadastro.getCep());
//            pstm.setString(17, cadastro.getChave_pix());
//            pstm.setFloat(18, cadastro.getLimite_diario());
//            pstm.setFloat(19, cadastro.getLimite_noturno());
//            pstm.setString(20, cript.encryptIntToBase64(cadastro.getSenha_transacoes()));
//            pstm.setString(21, cript.encryptIntToBase64(cadastro.getSenha_seguranca()));
//            pstm.setString(22, cadastro.getInstituicao_financeira());
//            pstm.setInt(23, cadastro.getNumero_conta());
//            pstm.setString(24, cadastro.getEmail());
//            pstm.setString(25, cadastro.getTipo_chave_pix());
//            pstm.setString(26, cadastro.getSexo());
//            pstm.setString(27, cadastro.getTelefone());
//            pstm.setString(28, cadastro.getNacionalidade());
//            pstm.setString(29, cript.encryptIntToBase64(cadastro.getSenha_app()));
//
//
//            pstm.execute();
//            String resposta = "cliente cadastrado com sucesso";
//            return resposta;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
}
