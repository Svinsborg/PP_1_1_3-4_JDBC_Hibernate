package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        UserService user = new UserServiceImpl();

        // Создание таблицы User(ов)
        user.createUsersTable();
        printUserTable();

        // Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод
        // в консоль ( User с именем – name добавлен в базу данных )
        user.saveUser("Duda","Red", (byte) 12);
        user.saveUser("Lasi","Grud", (byte) 12);
        user.saveUser("Dima","Grand", (byte) 12);
        user.saveUser("Lilu","Trud", (byte) 12);

        // Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
        printUserTable();

        // Очистка таблицы User(ов)
        user.cleanUsersTable();
        printUserTable();

        // Удаление таблицы
        user.dropUsersTable();
        printUserTable();
    }

    private static void printUserTable(){
        UserService user = new UserServiceImpl();
        List<User> usersTable = new ArrayList<>();
        usersTable = user.getAllUsers();
        usersTable.forEach(System.out::println);
    }
}
