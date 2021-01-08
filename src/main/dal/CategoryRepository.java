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
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public CategoryRepository() throws SQLException {
        sqlClass = new SqlConnectionHandler();
        connection = sqlClass.getConnection();
    }


    public ObservableList<Category> loadCategories(){
        try{
            ObservableList<Category> categories = FXCollections.observableArrayList();
            String query = "SELECT * FROM Category ORDER BY id";
            statement = connect.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                Category  c = new Category(resultSet.getString("name"));
                categories.add(c);
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
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
        int returnId = -1;
        try{
            String name = categoryToAdd.getName();
            String query ="INSERT INTO Category SET name ='"+name+"'";
            preparedStatement = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()){
                returnId = generatedKeys.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnId;
    }

    public void update(Category categoryToUpdate){
        try {
            String name = categoryToUpdate.getName();
            int id = categoryToUpdate.getId();
            String query = "UPDATE Category SET name = '"+name+"' WHERE id = '"+id+"'";
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
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

