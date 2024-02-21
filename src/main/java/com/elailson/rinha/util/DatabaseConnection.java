package com.elailson.rinha.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseConnection {

    private static final Logger log = Logger.getLogger(DatabaseConnection.class.getName());

    private static Connection con = null;

    private static final String CONNECTION_URL = "jdbc:postgresql://localhost:5432/rinha";
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String USERNAME = "rinha_backend";
    private static final String PASSWORD = "rinha_backend";

    private DatabaseConnection() {
    }

    static {
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            log.info(String.format("Houve um erro ao tentar conectar ao banco de dados: [%s].", e.getMessage()));
        }
    }

    public static Connection getConnection() {
        return con;
    }

}
