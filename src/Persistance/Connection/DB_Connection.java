package Persistance.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connection {

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyPos"
                    ,"root", "Mohid123"
            );
        }
        return connection;
    }


    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }



}
