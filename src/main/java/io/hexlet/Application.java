package io.hexlet;

import java.sql.DriverManager;
import java.sql.SQLException;
public class Application {
    public static void main(String[] args) throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test")) {

            try (var statement = conn.createStatement()) {
                var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
                statement.execute(sql);
            }

            try (var statement = conn.createStatement()) {
                var sql = "INSERT INTO users (username, phone) VALUES ('tommy', '123456789')";
                statement.executeUpdate(sql);
            }

            try (var statement = conn.createStatement()) {
                var sql = "SELECT * FROM users";
                try (var resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        System.out.println(resultSet.getString("username"));
                        System.out.println(resultSet.getString("phone"));
                    }
                }
            }
        }
    }
}
