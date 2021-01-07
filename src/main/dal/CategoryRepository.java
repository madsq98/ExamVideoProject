package main.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javafx.collections.ObservableList;
import main.be.Category;
import main.be.Video;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoryRepository {
    private SQLServerDataSource dataSource;
    private Connection connection;

    public CategoryRepository() {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setDatabaseName("EXAM_VIDEO");
        dataSource.setUser("CSe20A_30");
        dataSource.setPassword("mikkelsen");
        try {
            connection = dataSource.getConnection();
            Category c = new Category("test");
            c.setId(1);

            delete(c);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


    public ObservableList<Category> loadCategories(){


        return null;
    }

    public void delete(Category categoryToDelete) {
        try {
            String sql = "DELETE FROM Category WHERE ID = " + categoryToDelete.getId() + ";";
            PreparedStatement st = connection.prepareStatement(sql);
            st.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public int add(Category categoryToAdd){


        return 0;
    }

    public void update(Category categoryToUpdate){


    }

    public void saveAllLinks(Category c){

    }

    public void ObservableList(Category c){

    }

    public void saveLink(Category c, Video v){

    }

    public void deleteLink(Category c,Video v){

    }
}

