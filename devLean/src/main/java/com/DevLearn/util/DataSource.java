package com.DevLearn.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private String url = "jdbc:mysql://localhost:3306/devlearn";
    private String user = "root";
    private String password = "";
    private Connection connection;

    public DataSource() {
        connect();
    }

    private void connect() {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        if (connection == null || !isValidConnection()) {
            connect();
        }
        return connection;
    }

    private boolean isValidConnection() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
