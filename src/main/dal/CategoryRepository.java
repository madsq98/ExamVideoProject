package main.dal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.be.Category;
import main.be.Video;

import java.sql.*;

public class CategoryRepository {
    private VideoRepository vRepo;

    private SqlConnectionHandler sqlClass;
    private Connection connection;
    private PreparedStatement preparedStatement = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public CategoryRepository() throws SQLException {
        vRepo = new VideoRepository();

        sqlClass = new SqlConnectionHandler();
        connection = sqlClass.getConnection();
    }

    //Loads all the Categories from the database and returns it.
    public ObservableList<Category> loadCategories() throws SQLException {
        ObservableList<Category> categories = FXCollections.observableArrayList();

        //HACKFIX: ADD << ALL VIDEOS >> CATEGORY, TO SHOW ALL VIDEOS
        Category allVidsCat = new Category("<< ALL VIDEOS >>");
        allVidsCat.setId(-1);

        categories.add(allVidsCat);

        String query = "SELECT * FROM Category ORDER BY id";

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);

        while(rs.next()) {
            Category c = new Category(rs.getString("name"));
            c.setId(rs.getInt("ID"));
            c.addVideos(getLinkedMovies(c));
            categories.add(c);
        }

        return categories;
    }


    public ObservableList<Video> getLinkedMovies(Category c) throws SQLException {
        ObservableList<Video> movies = FXCollections.observableArrayList();
        String query = "SELECT * FROM CatMovie WHERE CategoryId = ?;";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,c.getId());
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            movies.add(vRepo.getMovieFromId(resultSet.getInt("MovieId")));
        }
        return movies;
    }
    //Deletes a Category in the database table "category".
    public void delete(Category categoryToDelete) throws SQLException {
        String sql = "DELETE FROM Category WHERE ID = ?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1,categoryToDelete.getId());
        st.executeUpdate();
    }
    //Adds a  new Category to the Category table. The added values will only be String name since ID will autoincrement.
    //returns the matched ID.
    public int add(Category categoryToAdd) throws SQLException {
        int returnId = -1;
        String name = categoryToAdd.getName();
        String query ="INSERT INTO Category (name) VALUES (?);";
        preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,categoryToAdd.getName());
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
        String query = "UPDATE Category SET name = ? WHERE id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,categoryToUpdate.getName());
        preparedStatement.setInt(2,categoryToUpdate.getId());
        preparedStatement.executeUpdate();
    }

    public void saveLink(Category c, Video v) throws SQLException{
        String query = "INSERT INTO CatMovie (CategoryId,MovieId) VALUES(?,?);";
        System.out.println(c.getId());
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,c.getId());
        preparedStatement.setInt(2,v.getId());
        preparedStatement.executeUpdate();
    }

    public void deleteLink(Category c,Video v) throws SQLException{
        String query = "DELETE FROM CatMovie WHERE CategoryId=? AND MovieId=?;";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,c.getId());
        preparedStatement.setInt(2,v.getId());
        preparedStatement.executeUpdate();
    }
}

