package br.com.pix.tcc.dao;

import br.com.pix.tcc.business.Cript;
import br.com.pix.tcc.config.DatabaseConfig;
import br.com.pix.tcc.domain.Dados;
import br.com.pix.tcc.domain.DadosDestinatario;
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


    public Boolean consulta_clientes(PixRequest pixRequest,String cpf) {

        String sql = "SELECT * FROM consulta_basica_cliente WHERE cpf_cnpj ='" + pixRequest.getCpf_remetente() + "' OR cpf_cnpj ='"+ cpf + "'";

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
                consulta.setCpf_cnpj(rst.getString("cpf_cnpj"));
                consulta.setSenha_app(rst.getString("senha_app"));
                contador++;
            }
            System.out.println(consulta.getCpf_cnpj());
            if(contador >0){
                if (consulta.getCpf_cnpj() != null) {
                    return true;
                } else {
                    return false;
                }
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Dados getDados(String cpf){

        String sql = "SELECT senha,senha_seguranca,limite_diario,limite_noturno,saldo,instituicao_financeira,numero_conta,nome,sobrenome,rastreavel FROM consulta_basica_cliente WHERE cpf_cnpj ='"+ cpf +"'";

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
                senhas.setLimiteDiario(rst.getFloat("limite_diario"));
                senhas.setLimiteNoturno(rst.getFloat("limite_noturno"));
                senhas.setSaldo(rst.getFloat("saldo"));
                senhas.setBanco_remetente(rst.getString("instituicao_financeira"));
                senhas.setNumero_conta_pagador(rst.getInt("numero_conta"));
                senhas.setNome(rst.getString("nome")+" "+(rst.getString("sobrenome")));
                senhas.setRastreavel(rst.getBoolean("rastreavel"));

            }
        if(senhas.getSenha() == null || senhas.getSenhaSeguranca() == null || senhas.getSenha()  == "" || senhas.getSenhaSeguranca() == "" ||
        senhas.getLimiteDiario() == null || senhas.getLimiteNoturno() == null){
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

    public DadosDestinatario getDadosDestinatario(String chavePix){

        String sql = "SELECT instituicao_financeira,numero_conta,nome,sobrenome,rastreavel,cpf_cnpj FROM consulta_basica_cliente WHERE chave_pix ='"+ chavePix +"'";

        List<CadastroRequest> cadastros = new ArrayList<CadastroRequest>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rst = null;
        DadosDestinatario dadosDestinatario = new DadosDestinatario();
        try {

            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            rst = pstm.executeQuery();
            CadastroRequest consulta = new CadastroRequest();
            String senha = null;
            String senha2 = null;

            while (rst.next()) {
                dadosDestinatario.setBanco_destinatario(rst.getString("instituicao_financeira"));
                dadosDestinatario.setNome_destinatario(rst.getString("nome")+" "+(rst.getString("sobrenome")));
                dadosDestinatario.setCpf_destinatario(rst.getString("cpf_cnpj"));
                dadosDestinatario.setNumero_conta_destinatario(rst.getInt("numero_conta"));
                dadosDestinatario.setRastreavel(rst.getBoolean("rastreavel"));

            }
            if(dadosDestinatario.getCpf_destinatario() == null || dadosDestinatario.getCpf_destinatario()   == "" ){
                DadosDestinatario erro = new DadosDestinatario();
                erro.setErro("Não foi possivel recuperar os dados do Destinatario");
                return erro;

            } else {

                return dadosDestinatario;
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

            if (rastreavel == "false" || rastreavel.isEmpty() || rastreavel ==null) {
                return false;
            } else {
                return true;

            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
