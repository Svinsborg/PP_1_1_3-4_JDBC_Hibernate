package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL_IN = "jdbc:mysql://10.10.0.118:3306/kata";
    private static final String DRIVER_OLD = "com.mysql.jdbc.Driver";
    private static final String DRIVER_NEW = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "kata";
    private static final String PASSWORD = "KataKata";

    public Connection getConnection() {
        Connection conn = null;

        try {
            Class.forName(DRIVER_NEW);
            conn = DriverManager.getConnection(URL_IN,USER,PASSWORD);
            System.out.println("Connection SUCCESS!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Connection ERR!" + ">>" + e);
        }
        return conn;
    }
}
