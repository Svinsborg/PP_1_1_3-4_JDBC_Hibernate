package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        UserService user = new UserServiceImpl();

        user.createUsersTable();
        printUserTable();

        user.saveUser("Duda","Red", (byte) 12);
        user.saveUser("Lasi","Grud", (byte) 12);
        user.saveUser("Dima","Grand", (byte) 12);
        user.saveUser("Lilu","Trud", (byte) 12);

        printUserTable();

        user.cleanUsersTable();
        printUserTable();

        user.dropUsersTable();
        printUserTable();

        Util.connectionClose();
    }

    private static void printUserTable(){
        UserService user = new UserServiceImpl();
        List<User> usersTable = new ArrayList<>();
        usersTable = user.getAllUsers();
        usersTable.forEach(System.out::println);
    }


}
