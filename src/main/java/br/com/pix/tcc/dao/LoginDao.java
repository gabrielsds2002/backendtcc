package br.com.pix.tcc.dao;

import br.com.pix.tcc.business.Cript;
import br.com.pix.tcc.business.ValidadorCPF_CNPJ;
import br.com.pix.tcc.config.DatabaseConfig;
import br.com.pix.tcc.config.TokenConfig;
import br.com.pix.tcc.domain.Response.LoginResponse;
import br.com.pix.tcc.domain.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginDao {


    TokenConfig tokenConfig;
    private Cript cript;

    ValidadorCPF_CNPJ validadorCPFCnpj;
    public LoginResponse login(LoginRequest login) {
        login.setCpf_cnpj(validadorCPFCnpj.limparCaracteresEspeciais(login.getCpf_cnpj()));
        String sql = "SELECT * FROM consulta_basica_cliente WHERE cpf_cnpj = '" + login.getCpf_cnpj() + "'";
        LoginResponse loginResponse = new LoginResponse();

        List<LoginRequest> cadastros = new ArrayList<LoginRequest>();

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rst = null;

        try {

            conn = DatabaseConfig.criaConexao();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            rst = pstm.executeQuery();
            LoginRequest consulta = new LoginRequest();
            LoginResponse response = new LoginResponse();

            while (rst.next()) {


                consulta.setCpf_cnpj(rst.getString("cpf_cnpj"));
                consulta.setSenha(rst.getString("senha_app"));

                System.out.println(consulta.getSenha());


            }



            if (consulta.getCpf_cnpj() == null) {
                loginResponse.setMensagem("Usuario não encontrado");
                loginResponse.setStatus(false);
                return loginResponse;
            } else {
                System.out.println(consulta.getSenha());
                consulta.setSenha(cript.decryptIntFromBase64(consulta.getSenha()));
                System.out.println(consulta.getSenha());
                System.out.println(login.getSenha());
                if(consulta.getSenha().contains(login.getSenha())){
                    loginResponse.setMensagem("Usuario encontrado!");
                    loginResponse.setStatus(true);
                    return loginResponse;

                }else{
                    loginResponse.setMensagem("Senha Invalida");
                    loginResponse.setStatus(false);
                    return loginResponse;
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }






}
