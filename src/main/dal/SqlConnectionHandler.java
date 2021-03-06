package main.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import main.be.Category;

import java.sql.Connection;
import java.sql.SQLException;

public class SqlConnectionHandler {
    private SQLServerDataSource dataSource;
    private Connection connection;

    /**
     * Constructor. Instantiates the connection to the database
     * @throws SQLException SQL Error
     */
    public SqlConnectionHandler() throws SQLException {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setDatabaseName("EXAM_VIDEO");
        dataSource.setUser("CSe20A_30");
        dataSource.setPassword("mikkelsen");

        connection = dataSource.getConnection();
    }

    /**
     * Gets the connection
     * @return connection
     */
    public Connection getConnection() {
        if(connection != null) {
            return connection;
        } else {
            return null;
        }
    }
}
