package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.lang.AutoCloseable;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoJDBCImpl();
        UserService userService = new UserServiceImpl((UserDaoJDBCImpl) userDao);
        try {
            userService.createUsersTable();

            userService.saveUser("Grigoriy", "Fedorov", (byte) 28);
            userService.saveUser("Джеймс", "Гослинг", (byte) 69);
            userService.saveUser("Павел", "Дуров", (byte) 39);
            userService.saveUser("Илон", "Маск", (byte) 52);

            System.out.println(userService.getAllUsers());
            userService.getAllUsers();
            userService.dropUsersTable();
        } finally {
            Connection connection = Util.getConnection();
            Util.closeConnection(connection);
        }
    }
}
