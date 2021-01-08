package main.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.be.Category;
import main.be.Video;

import java.sql.*;
import java.time.LocalDate;

public class CategoryRepository {
    private SqlConnectionHandler sqlClass;
    private Connection connection;

    public CategoryRepository() throws SQLException {
        sqlClass = new SqlConnectionHandler();
        connection = sqlClass.getConnection();
    }


    public ObservableList<Category> loadCategories() throws SQLException {
        String query = "SELECT * FROM Category";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);

        ObservableList<Category> returnList = FXCollections.observableArrayList();

        while(rs.next()) {
            int id = rs.getInt("ID");
            String name = rs.getString("name");

            Category c = new Category(name);
            c.setId(id);

            returnList.add(c);
        }

        return returnList;

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

    public int add(Category categoryToAdd) throws SQLException {
        String query = "INSERT INTO CATEGORY (name) VALUES (?);";
        PreparedStatement st = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        st.setString(1, categoryToAdd.getName());

        int affectedRows = st.executeUpdate();

        ResultSet keys = st.getGeneratedKeys();

        if(keys.next()) {
            return keys.getInt(1);
        } else {
            return 0;
        }
    }

    public void update(Category categoryToUpdate) throws SQLException {
        String query = "UPDATE CATEGORY SET name = ? WHERE ID = ?;";
        PreparedStatement st = connection.prepareStatement(query);

        st.setString(1, categoryToUpdate.getName());


        st.executeUpdate();
    }




    public void saveAllLinks(Category c){
        try {
            String sql = "";
            PreparedStatement st = connection.prepareStatement(sql);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ObservableList(Category c){
        try {
            String sql = "";
            PreparedStatement st = connection.prepareStatement(sql);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveLink(Category c, Video v){
        try {
            String sql = "";
            PreparedStatement st = connection.prepareStatement(sql);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLink(Category c,Video v){
        try {
            String sql = "UPDATE Movie SET [path] = REPLACE(path ?) WHERE path =?";;
            PreparedStatement st = connection.prepareStatement(sql);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

