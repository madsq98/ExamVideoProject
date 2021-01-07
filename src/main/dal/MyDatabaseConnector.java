package main.dal;


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class MyDatabaseConnector {

    /**
     * MyDatabaseConnector makes the connection to the database. We have a main method here as well
     * to test the connection.
     */

    private SQLServerDataSource dataSource;

    public MyDatabaseConnector()
    {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("11.0.7493.4");
        dataSource.setDatabaseName("mikkelsen");
        dataSource.setUser("CSe20A_30");
        dataSource.setPassword("mikkelsen");
    }

    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }

    public static void main(String[] args) throws SQLException, IOException {
        MyDatabaseConnector databaseConnector = new MyDatabaseConnector();
        Connection connection = databaseConnector.getConnection();

        System.out.println("Is it open? " + !connection.isClosed());

        connection.close();
    }
}
