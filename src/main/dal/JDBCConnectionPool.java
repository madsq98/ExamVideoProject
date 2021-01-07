package main.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCConnectionPool extends ObjectPool<Connection> {

    private static JDBCConnectionPool INSTANCE;
    private final MyDatabaseConnector connectionProvider;


    public synchronized static JDBCConnectionPool getInstance() throws IOException, SQLServerException {
        if (INSTANCE == null)
            INSTANCE = new JDBCConnectionPool();
        return INSTANCE;
    }

    private JDBCConnectionPool() throws IOException {
        connectionProvider = new MyDatabaseConnector();
    }

    protected Connection create() throws SQLException {
        try {
            return connectionProvider.getConnection();
        } catch (SQLServerException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean validate(Connection con)
    {
        try {
            return (!con.isClosed());
        } catch (SQLException e)
        {
            e.printStackTrace();
            return (false);
        }
    }

    public void expire(Connection con)
    {
        try {
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}