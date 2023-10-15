package io.hexlet;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO {

    private static final String CREATE_TABLE_STATEMENT = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
    private static final String INSERT_STATEMENT = "INSERT INTO users (username, phone) VALUES (?, ?)";
    private static final String SELECT_ALL_STATEMENT = "SELECT id, username, phone FROM users";
    private static final String SELECT_BY_ID_STATEMENT = "SELECT username, phone FROM users WHERE id = ?";
    private static final String DELETE_BY_ID_STATEMENT = "DELETE FROM users WHERE id = ?";
    private static final String ID_ERROR_MESSAGE = "DB have not returned an id after saving an entity";

    private final Connection connection;

    public UserDAO(Connection connection) throws SQLException {
        this.connection = connection;

        try (var statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE_STATEMENT);
        }
    }

    public void save(User user) throws SQLException {
        try (var preparedStatement = connection.prepareStatement(INSERT_STATEMENT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPhone());
            preparedStatement.executeUpdate();
            try (var generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException(ID_ERROR_MESSAGE);
                }
            }
        }
    }

    public List<User> findAll() throws SQLException {
        List<User> result = new ArrayList<>();

        try (var statement = connection.createStatement()) {
            try (var resultSet = statement.executeQuery(SELECT_ALL_STATEMENT)) {
                while (resultSet.next()) {
                    var id = resultSet.getLong("id");
                    var username = resultSet.getString("username");
                    var phone = resultSet.getString("phone");
                    var user = new User(id, username, phone);
                    result.add(user);
                }
            }
        }

        return result;
    }

    public Optional<User> find(long id) throws SQLException {
        try (var preparedStatement = connection.prepareStatement(SELECT_BY_ID_STATEMENT)) {
            preparedStatement.setLong(1, id);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    var username = resultSet.getString("username");
                    var phone = resultSet.getString("phone");
                    var user = new User(id, username, phone);
                    return Optional.of(user);
                }
            }
        }
        return Optional.empty();
    }

    public void delete(long id) throws SQLException {
        try (var preparedStatement = connection.prepareStatement(DELETE_BY_ID_STATEMENT)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
