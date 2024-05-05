package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "roots";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Соединение установлено");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Соединение не установлено");
        }
        return connection;

    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Соединение закрыто");
            } catch (SQLException e) {
                // реализуйте настройку соеденения с БД
            }
        }
    }
}
