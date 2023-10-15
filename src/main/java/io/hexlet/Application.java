package io.hexlet;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Application {

    private static final String CREATE_STATEMENT = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
    private static final String INSERT_STATEMENT = "INSERT INTO users (username, phone) VALUES (?, ?)";
    private static final String SELECT_STATEMENT = "SELECT username, phone FROM users";
    private static final String DELETE_STATEMENT = "DELETE FROM users WHERE username = ?";

    public static void main(String[] args) throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test")) {

            try (var statement = conn.prepareStatement(CREATE_STATEMENT)) {
                statement.execute();
            }

            try (var statement = conn.prepareStatement(INSERT_STATEMENT, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, "Tommy");
                statement.setString(2, "1234567");
                statement.executeUpdate();
                try (var generatedKeys = statement.getGeneratedKeys()) {
                    for (int number = 1; generatedKeys.next(); number++) {
                        System.out.printf("key#%d: %d%n", number, generatedKeys.getLong(number));
                    }
                }

                statement.setString(1, "Andy");
                statement.setString(2, "7654321");
                statement.executeUpdate();
                try (var generatedKeys = statement.getGeneratedKeys()) {
                    for (int number = 1; generatedKeys.next(); number++) {
                        System.out.printf("key#%d: %d%n", number, generatedKeys.getLong(number));
                    }
                }
            }

            try (var statement = conn.prepareStatement(SELECT_STATEMENT)) {
                try (var resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        System.out.println(resultSet.getString("username"));
                        System.out.println(resultSet.getString("phone"));
                    }
                }
            }

            try (var statement = conn.prepareStatement(DELETE_STATEMENT)) {
                statement.setString(1, "Tommy");
                statement.executeUpdate();
            }


            try (var statement = conn.prepareStatement(SELECT_STATEMENT)) {
                try (var resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        System.out.println(resultSet.getString("username"));
                        System.out.println(resultSet.getString("phone"));
                    }
                }
            }
        }
    }
}
