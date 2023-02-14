package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }
    Connection con = Util.getConnection();
    public void createUsersTable() {

        try(Statement st = con.createStatement()) {
            int res = st.executeUpdate(SQLQuery.CREATE.QUERY);
            System.out.println("Создание таблицы: " + res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Statement st = con.createStatement()) {
            int res = st.executeUpdate(SQLQuery.DROP.QUERY);
            System.out.println("Удаление таблицы: " + res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try(PreparedStatement ps = con.prepareStatement(SQLQuery.SAVE.QUERY)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.execute();
            System.out.println("Пользователь с именем " + name + " добавлен!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        try (PreparedStatement ps = con.prepareStatement(SQLQuery.REMOVE.QUERY)){
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Statement st = con.createStatement()){

            ResultSet res = st.executeQuery(SQLQuery.GET.QUERY);

                while (res.next()) {
                    User user = new User();
                    user.setId((long) res.getInt("id"));
                    user.setName(res.getString("name"));
                    user.setLastName(res.getString("lastName"));
                    user.setAge(res.getByte("age"));

                    users.add(user);
                }

        } catch (SQLException e) {
            System.out.println("Данные отсутствуют ! >> " + e);
        }

        return users;
    }

    public void cleanUsersTable() {
        try(Statement st = con.createStatement()) {
            int res = st.executeUpdate(SQLQuery.CLEAN.QUERY);
            System.out.println("Очистка таблицы: " + res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    enum SQLQuery{
        CREATE("CREATE TABLE IF NOT EXISTS `kata`.`users` (" +
                "`id` INT NOT NULL AUTO_INCREMENT," +
                "`name` VARCHAR(20) NULL," +
                "`lastName` VARCHAR(30) NULL,`" +
                "age` INT NULL,PRIMARY KEY (`id`)) " +
                "ENGINE = InnoDB " +
                "DEFAULT CHARACTER SET = utf8;"),
        DROP("DROP TABLE IF EXISTS `kata`.`users`;"),
        SAVE("INSERT INTO `kata`.`users` (`name`, `lastName`, `age`) VALUES (?, ?, ?);"),
        REMOVE("DELETE FROM `kata`.`users` WHERE (users.id = ?) LIMIT 1;"),
        GET("SELECT `id`, `name`, `lastName`, `age` FROM kata.users;"),
        CLEAN("TRUNCATE `kata`.`users`;");

        String QUERY;

        SQLQuery(String QUERY) { this.QUERY = QUERY; }
    }
}
