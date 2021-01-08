package main.dal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.be.Video;
import main.util.DateConverter;

import java.sql.*;
import java.time.LocalDate;

public class VideoRepository {
    private SqlConnectionHandler sqlClass;
    private Connection connection;

    public VideoRepository() throws SQLException {
        sqlClass = new SqlConnectionHandler();
        connection = sqlClass.getConnection();
    }

    public ObservableList<Video> loadVideos() throws SQLException {
        String query = "SELECT * FROM MOVIE";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        ObservableList<Video> returnList = FXCollections.observableArrayList();

        while(rs.next()) {
            int id = rs.getInt("ID");
            String name = rs.getString("name");
            double rating = rs.getDouble("rating");
            String path = rs.getString("path");
            LocalDate lastView = rs.getDate("lastview").toLocalDate();

            Video v = new Video(name,path);
            v.setId(id);
            v.setRating(rating);
            v.setLastView(lastView);
            returnList.add(v);
        }

        return returnList;
    }

    public void delete(Video videoToDelete) throws SQLException {
        String query = "DELETE FROM MOVIE WHERE ID = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, videoToDelete.getId());
        st.executeUpdate();
    }

    public int add(Video videoToAdd) throws SQLException {
        String query = "INSERT INTO MOVIE (name, rating, path, lastview) VALUES (?, ?, ?, ?);";
        PreparedStatement st = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        st.setString(1, videoToAdd.getName());
        st.setDouble(2, videoToAdd.getRating());
        st.setString(3, videoToAdd.getPath());
        st.setDate(4, java.sql.Date.valueOf(videoToAdd.getLastView()));

        int affectedRows = st.executeUpdate();

        ResultSet keys = st.getGeneratedKeys();

        if(keys.next()) {
            return keys.getInt(1);
        } else {
            return 0;
        }
    }

    public void update(Video videoToUpdate) throws SQLException {
        String query = "UPDATE MOVIE SET name = ?, rating = ?, path = ?, lastview = ? WHERE ID = ?;";
        PreparedStatement st = connection.prepareStatement(query);

        st.setString(1, videoToUpdate.getName());
        st.setDouble(2, videoToUpdate.getRating());
        st.setString(3, videoToUpdate.getPath());
        st.setDate(4, java.sql.Date.valueOf(videoToUpdate.getLastView()));

        st.executeUpdate();
    }
}
