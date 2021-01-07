package main.dal;


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import sample.be.Playlist;
import sample.be.Song;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class MyDatabaseConnector {

    /**
     * MyDatabaseConnector makes the connection to the database. We have a main method here as well
     * to test the connection.
     */

    private SQLServerDataSource dataSource;

    public MyDatabaseConnector()
    {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setDatabaseName("MyTunes101");
        dataSource.setUser("CSe20A_26");
        dataSource.setPassword("CSe20A_26");
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
