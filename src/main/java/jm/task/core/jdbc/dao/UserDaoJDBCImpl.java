package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {


    }

    public void createUsersTable()  {

        try(Statement st = getConnection().createStatement()) {
            int res = st.executeUpdate(SQLQuery.CREATE.QUERY);
            System.out.println("Создание таблицы: " + res);
/*            st.addBatch("INSERT INTO `kata`.`users` (`name`, `lastName`, `age`) VALUES ('Andy', 'Filth', '22');");
            st.addBatch("INSERT INTO `kata`.`users` (`name`, `lastName`, `age`) VALUES ('Bob', 'Sud', '31');");
            st.addBatch("INSERT INTO `kata`.`users` (`name`, `lastName`, `age`) VALUES ('John', 'Jons', '45');");
            st.addBatch("INSERT INTO `kata`.`users` (`name`, `lastName`, `age`) VALUES ('Filip', 'Big', '27');");
            int[] ress = st.executeBatch();
            System.out.println("Внесение данных таблицы: " + Arrays.toString(ress));
            st.clearBatch();*/

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Statement st = getConnection().createStatement()) {
            int res = st.executeUpdate(SQLQuery.DROP.QUERY);
            System.out.println("Удаление таблицы: " + res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try(PreparedStatement ps = getConnection().prepareStatement(SQLQuery.SAVE.QUERY)) {
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

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(SQLQuery.REMOVE.QUERY)){
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Statement st = getConnection().createStatement()){

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
        try(Statement st = getConnection().createStatement()) {
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
