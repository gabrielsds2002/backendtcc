package br.com.pix.tcc.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "2B3gfH5-BeG2FfDgf2BC-hfAAfC31Fe2";
    private static final String DATABASE_URL = "jdbc:mysql://monorail.proxy.rlwy.net:16911/railway";

    public static Connection criaConexao() throws Exception {
        try {


            System.out.println("Iniciando conexão com o bd!");
            Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}


