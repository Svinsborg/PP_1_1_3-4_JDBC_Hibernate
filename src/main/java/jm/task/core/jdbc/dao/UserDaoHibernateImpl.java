package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {
        Transaction tx = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Query createQuery = session.createSQLQuery(
                    "CREATE TABLE IF NOT EXISTS `kata`.`users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(20) NULL,\n" +
                    "  `lastName` VARCHAR(30) NULL,\n" +
                    "  `age` INT NULL,\n" +
                    "  PRIMARY KEY (`id`))\n" +
                    "ENGINE = InnoDB\n" +
                    "DEFAULT CHARACTER SET = utf8;");
            createQuery.executeUpdate();
            tx.commit();
            session.close();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tx = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Query createQuery = session.createSQLQuery(
                    "DROP TABLE IF EXISTS `kata`.`users`;");
            createQuery.executeUpdate();
            tx.commit();
            session.close();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            System.out.println("Пользователь с именем " + name + " добавлен!");
            tx.commit();
            session.close();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) { //TODO
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = new User();
            user.setId(id);
            session.delete(user);
            tx.commit();
            session.close();
        } catch (Exception e){
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void cleanUsersTable() { //TODO
        Transaction tx = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Query createQuery = session.createSQLQuery(
                    "TRUNCATE `kata`.`users`;");
            int res = createQuery.executeUpdate();
            tx.commit();
            session.close();
            System.out.println("Clear : " + res );
        } catch (Exception e){
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
}
