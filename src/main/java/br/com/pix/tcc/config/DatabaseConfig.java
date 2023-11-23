package br.com.pix.tcc.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConfig {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "150702";
    private static final String DATABASE_URL = "jdbc:mysql://localhost/tcc";

    public static Connection criaConexao() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Iniciando conexão com o bd!");
        Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);

        return connection;
    }
}


