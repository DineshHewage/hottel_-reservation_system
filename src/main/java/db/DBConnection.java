package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance = null;
    private Connection connection;

    private DBConnection() throws SQLException {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation", "root", "Dinesh@12345");
    }
    public static DBConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnection();
            return instance;
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
