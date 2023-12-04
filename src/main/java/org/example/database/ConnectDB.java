package org.example.database;

import java.sql.*;

public class ConnectDB {
    private static final String user_nam_db = "root";
    private static final String pass_word = "root";
    private static final String connectionURL = "jdbc:mysql://loginservicedb.chat-app.svc.cluster.local:3306/chat_app";

    public Connection connect() {
        Connection conn = null;
        try {
            System.out.println("Kết nối thành công");
            conn = DriverManager.getConnection(connectionURL, user_nam_db, pass_word);
            System.out.println("Kết nối thành công");
        } catch (SQLException e1) {
            System.out.println(e1.getMessage() + "  Connect");
        } catch (Exception e) {
            System.out.println(e.getMessage() + "  Connect");
        }

        return conn;
    }
}
