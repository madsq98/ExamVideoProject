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

    /**
     * Constructor for Video Repository
     * @throws SQLException SQL Error
     */
    public VideoRepository() throws SQLException {
        sqlClass = new SqlConnectionHandler();
        connection = sqlClass.getConnection();
    }

    /**
     * Loads videos from database
     * @return List of videos
     * @throws SQLException SQL Error
     */
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

    /**
     * Deletes video from database
     * @param videoToDelete video to delete
     * @throws SQLException SQL Error
     */
    public void delete(Video videoToDelete) throws SQLException {
        deleteAllLinks(videoToDelete);

        String query = "DELETE FROM MOVIE WHERE ID = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, videoToDelete.getId());
        st.executeUpdate();
    }

    /**
     * Adds video to database
     * @param videoToAdd video to add
     * @return id of the added video
     * @throws SQLException SQL Error
     */
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

    /**
     * Updates video in database, by using the id
     * @param videoToUpdate video to update
     * @throws SQLException SQL Error
     */
    public void update(Video videoToUpdate) throws SQLException {
        String query = "UPDATE MOVIE SET name = ?, rating = ?, path = ?, lastview = ? WHERE ID = ?;";
        PreparedStatement st = connection.prepareStatement(query);

        st.setString(1, videoToUpdate.getName());
        st.setDouble(2, videoToUpdate.getRating());
        st.setString(3, videoToUpdate.getPath());
        st.setDate(4, java.sql.Date.valueOf(videoToUpdate.getLastView()));
        st.setInt(5,videoToUpdate.getId());

        st.executeUpdate();
    }

    /**
     * Gets movie from id
     * @param id id to search
     * @return video
     * @throws SQLException SQL Error
     */
    public Video getMovieFromId(int id) throws SQLException {
        String query = "SELECT * FROM MOVIE WHERE ID = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1,id);
        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            Video v = new Video(rs.getString("name"),rs.getString("path"));
            v.setId(id);
            v.setRating(rs.getDouble("rating"));
            v.setLastView(rs.getDate("lastview").toLocalDate());

            return v;
        } else {
            return null;
        }
    }

    /**
     * Check if video exsists
     * @param v video to check
     * @return true if video exists, false if not
     * @throws SQLException SQL Error
     */
    public boolean exists(Video v) throws SQLException {
        String query = "SELECT * FROM MOVIE WHERE name = ? OR path = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1,v.getName());
        st.setString(2,v.getPath());
        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Private function used for deleting all links between videos and categories
     * @param v Video to delete all links for
     * @throws SQLException SQL Error
     */
    private void deleteAllLinks(Video v) throws SQLException {
        String query = "DELETE FROM CatMovie WHERE MovieId = ?;";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1,v.getId());
        st.executeUpdate();
    }
}
