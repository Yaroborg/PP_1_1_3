package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoHibernateImpl.class);

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        executeQuery(
                "CREATE TABLE IF NOT EXISTS users" +
                        " (id INTEGER not NULL AUTO_INCREMENT," +
                        " name VARCHAR (45) not NULL," +
                        " last_name VARCHAR (45) not NULL," +
                        " age INT (100) not NULL," +
                        " PRIMARY KEY (id))",
                "Таблица users успешно создана."
        );
    }

    @Override
    public void dropUsersTable() {
        executeQuery(
                "DROP TABLE IF EXISTS users",
                "Таблица users успешно удалена."
        );
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(new User(name, lastName, age));

            transaction.commit();

            logger.info("Пользователь с именем {}, фамилией {} и возрастом {} добавлен.", name, lastName, age);

        } catch (Exception exception) {
            exceptionHandler(exception, transaction);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User userToDelete = session.get(User.class, id);
            if (userToDelete != null) {
                session.remove(userToDelete);
                transaction.commit();
                logger.info("Пользователь с id {} удален.", id);
            } else {
                logger.warn("Пользователь с id {} не найден.", id);
            }

        } catch (Exception exception) {
            exceptionHandler(exception, transaction);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            users = session.createNativeQuery("SELECT * FROM users", User.class).getResultList();
            transaction.commit();

            logger.debug("Получен список всех пользователей.");

        } catch (Exception exception) {
            exceptionHandler(exception, transaction);
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        executeQuery(
                "DELETE FROM users",
                "Таблица очищена, все данные удалены."
        );
    }

    private void executeQuery(String querySql, String message) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.createNativeQuery(querySql).executeUpdate();
            transaction.commit();
            logger.info(message);

        } catch (Exception exception) {
            exceptionHandler(exception, transaction);
        }
    }

    private void exceptionHandler(Exception exception, Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
        logger.error("Произошла ошибка", exception);
    }
}