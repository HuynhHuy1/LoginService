package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabase {
    private Connection connection;
    private final String url;
    private final String username;
    private final String password;

    public MySQLDatabase(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Disconnected from the database");
            } catch (SQLException e) {
                System.out.println("Error disconnecting from the database");
            }
        }
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/login_token";
        String username = "";
        String password = "";

        MySQLDatabase database = new MySQLDatabase(url, username, password);
        database.connect();

        database.disconnect();
    }
}
