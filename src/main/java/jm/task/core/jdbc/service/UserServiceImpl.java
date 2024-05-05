package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;
import java.util.logging.Logger;

public class UserServiceImpl implements UserService {
    private final UserDaoJDBCImpl user;

    public UserServiceImpl(UserDaoJDBCImpl user) {
        this.user = user;
    }

    public UserServiceImpl() {
        this.user = new UserDaoJDBCImpl();
    }

    @Override
    public void createUsersTable() {
        user.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        user.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        user.saveUser(name, lastName, age);
        Logger log = Logger.getLogger(UserServiceImpl.class.getName());
        log.info("User с именем – " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        user.removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return user.getAllUsers();
    }

    @Override
    public void cleanUsersTable() {
        user.dropUsersTable();
    }
}
