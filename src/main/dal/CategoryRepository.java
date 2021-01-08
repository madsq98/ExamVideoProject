package main.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.be.Category;
import main.be.Video;

import java.sql.*;

public class CategoryRepository {
    private SqlConnectionHandler sqlClass;
    private Connection connection;
    private PreparedStatement preparedStatement = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public CategoryRepository() throws SQLException {
        sqlClass = new SqlConnectionHandler();
        connection = sqlClass.getConnection();
    }


    public ObservableList<Category> loadCategories() throws SQLException {
        ObservableList<Category> categories = FXCollections.observableArrayList();
        String query = "SELECT * FROM Category ORDER BY id";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        while(resultSet.next()) {
            Category  c = new Category(resultSet.getString("name"));
            categories.add(c);
        }
        return categories;
    }

    public void delete(Category categoryToDelete) throws SQLException {
        String sql = "DELETE FROM Category WHERE ID = " + categoryToDelete.getId() + ";";
        PreparedStatement st = connection.prepareStatement(sql);
        st.executeUpdate();
    }

    public int add(Category categoryToAdd) throws SQLException {
        int returnId = -1;
        String name = categoryToAdd.getName();
        String query ="INSERT INTO Category SET name ='"+name+"'";
        preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if(generatedKeys.next()){
            returnId = generatedKeys.getInt(1);
        }
        return returnId;
    }

    public void update(Category categoryToUpdate) throws SQLException {
        String name = categoryToUpdate.getName();
        int id = categoryToUpdate.getId();
        String query = "UPDATE Category SET name = '"+name+"' WHERE id = '"+id+"'";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeUpdate();
    }

    public void saveAllLinks(Category c){

    }

    public void saveLink(Category c, Video v){

    }

    public void deleteLink(Category c,Video v){

    }
}

