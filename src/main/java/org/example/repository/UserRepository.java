package org.example.repository;

import org.example.database.ConnectDB;
import org.example.src.Model.User;

import java.sql.*;

public class UserRepository {
    private static ConnectDB connectDB;
    public UserRepository() {
        connectDB = new ConnectDB();
    }

    public User findUserByNameAndPassword(String username, String password) {
        User user = new User();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM users WHERE user_name = ? AND password = ?";
            ps = connectDB.connect().prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            rs = ps.executeQuery();


            if (!rs.isBeforeFirst()) {
                System.out.println("User not found in Database");
            } else {
                while (rs.next()) {
                    int userID = rs.getInt("id");
                    String retrievedPassword = rs.getString("password");
                    String retrievedUsername = rs.getString("user_name");
                    String retrievedEmail = rs.getString("email");
                    String retrievedPhone = rs.getString("phone");
                    user.setId(userID);
                    user.setUsername(retrievedUsername);
                    user.setPassword(retrievedPassword);
                    user.setEmail(retrievedEmail);
                    user.setPhone(retrievedPhone);
                    return user;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public User getUserById(int id) {
        User user = new User();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            ps = connectDB.connect().prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();


            if (!rs.isBeforeFirst()) {
                System.out.println("User not found in Database");
            } else {
                while (rs.next()) {
                    int retrievedId = rs.getInt("id");
                    String retrievedPassword = rs.getString("password");
                    String retrievedUsername = rs.getString("user_name");
                    String retrievedEmail = rs.getString("email");
                    String retrievedPhone = rs.getString("phone");
                    user.setUsername(retrievedUsername);
                    user.setPassword(retrievedPassword);
                    user.setEmail(retrievedEmail);
                    user.setPhone(retrievedPhone);
                    user.setId(retrievedId);
                    return user;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("hai nam");
        return null;
    }

    public User addUser(String username, String email, String password, String phone) {
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExits = null;
        ResultSet resultSet = null;
        User newUser = new User();
        Connection connection = connectDB.connect();

        try {
            String sqlCheckUser = "SELECT * FROM users WHERE user_name = ?";
            psCheckUserExits = connection.prepareStatement(sqlCheckUser);
            psCheckUserExits.setString(1, username);
            resultSet = psCheckUserExits.executeQuery();
            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists");
            } else {
                Timestamp createdAt = new Timestamp(System.currentTimeMillis());
                String sql = "INSERT INTO users (user_name, email, password, phone, created_at) VALUES (?, ?, ?, ?, ?)";
                psInsert = connection.prepareStatement(sql);
                psInsert.setString(1, username);
                psInsert.setString(2, email);
                psInsert.setString(3, password);
                psInsert.setString(4, phone);
                psInsert.setTimestamp(5, createdAt);

                psInsert.executeUpdate();


                newUser.setEmail(email);
                newUser.setUsername(username);
                newUser.setPassword(password);
                newUser.setPhone(phone);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (psInsert != null) {
                    psInsert.close();
                }
                if (psCheckUserExits != null) {
                    psCheckUserExits.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return newUser;
    }
}
