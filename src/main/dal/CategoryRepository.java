package main.dal;

import javafx.collections.ObservableList;
import main.be.Category;
import main.be.Video;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoryRepository {
    private Connection connection;
    public CategoryRepository() throws SQLException {
        String connectionUrl =
                "jdbc:sqlserver://mikkelsen.database.windows.net:11.0.7493.4;"
                        + "database=EXAM_VIDEO;"
                        + "user=CSe20A_30;"
                        + "password=mikkelsen;"
                        + "encrypt=true;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";
        connection = DriverManager.getConnection(connectionUrl);

        Category c = new Category("test");
        c.setId(1);

        try {
            delete(c);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }


    public ObservableList<Category> loadCategories(){


        return null;
    }

    public void delete(Category categoryToDelete) throws SQLException {
        String sql = "DELETE FROM Category WHERE ID = "+categoryToDelete.getId()+";";
        PreparedStatement st = connection.prepareStatement(sql);
        st.executeUpdate();
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

