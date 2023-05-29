package org.project.databases;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/project";
    private static final String USER = "postgres";
    private static final String PASSWORD = "parola";
    private static Connection connection = null;
    static HikariDataSource ds;

    private Database() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            createConnection();
        }
        return connection;
    }

    private static void createConnection() {
        try {
            createHikariCP();
            connection = ds.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public static void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    //for HikariCP
    public static void createHikariCP() {
        HikariConfig config = new HikariConfig("E:\\FACULTATE\\Advanced_Programming_(Java)\\Project-Java\\Project\\src\\main\\java\\org\\project\\hikari.properties");
        ds = new HikariDataSource(config);
    }
}