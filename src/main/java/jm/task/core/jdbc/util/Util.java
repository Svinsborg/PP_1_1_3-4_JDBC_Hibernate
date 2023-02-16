package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL_IN = "jdbc:mysql://10.10.0.118:3306/kata";
    private static final String DRIVER_OLD = "com.mysql.jdbc.Driver";
    private static final String DRIVER_NEW = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "kata";
    private static final String PASSWORD = "KataKata";
    private static Connection conn = null;
    public static Connection getConnection() {

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

    public static void connectionClose(){
        if(conn != null)
            try {
                conn.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
    }

    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://10.10.0.118:3306/kata");
                settings.put(Environment.USER, "kata");
                settings.put(Environment.PASS, "KataKata");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect"); // org.hibernate.dialect.MySQLInnoDBDialect

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.GENERATE_STATISTICS, "hibernate.generate_statistics");

                settings.put(Environment.HBM2DDL_AUTO, "");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("Ссессия создана!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
    public static void close() {
        getSessionFactory().close();
    }
}
