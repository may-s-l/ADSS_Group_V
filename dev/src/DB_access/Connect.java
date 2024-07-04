package DB_access;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
    private final static String url = "jdbc:sqlite:C:\\sqlite\\SuperLi.db";
    private static Connection connection = null;

    private static void connectionEstablished() {
        System.out.println("--Connection to the SuperLi database has been established--");
    }
    private static void connectionClosed() {
        System.out.println("--Connection to the SuperLi database has been closed--");
    }
    public static void checkConnection() {
        try {
            // db parameters
            // create a connection to the database
            connection = DriverManager.getConnection(url);
            connectionEstablished();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                connectionClosed();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void connect() {
        try {
            connection = DriverManager.getConnection(url);
            connectionEstablished();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            if (connection != null)
                connection.close();
            connectionClosed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection returnConnection() {
        return connection;
    }
}
