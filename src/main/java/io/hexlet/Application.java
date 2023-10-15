package io.hexlet;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {

    public static void main(String[] args) throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test")) {

            var userDao = new UserDAO(conn);

            var user1 = new User("Tommy", "1234567");
            userDao.save(user1);

            var user2 = new User("Andy", "7654321");
            userDao.save(user2);

            System.out.println(userDao.findAll());

            userDao.delete(1);

            System.out.println(userDao.findAll());
        }
    }
}
