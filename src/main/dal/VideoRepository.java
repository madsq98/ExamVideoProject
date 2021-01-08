package main.dal;

import javafx.collections.ObservableList;
import main.be.Video;
import main.util.DateConverter;

import java.sql.*;

public class VideoRepository {
    private SqlConnectionHandler sqlClass;
    private Connection connection;

    public VideoRepository() throws SQLException {
        sqlClass = new SqlConnectionHandler();
        connection = sqlClass.getConnection();
    }

    public ObservableList<Video> loadVideos() {
        //SELECT * FROM movie - returns a list of all videos from table movie
        return null;
    }

    public void delete(Video videoToDelete) throws SQLException {
        String query = "DELETE FROM MOVIE WHERE ID = "+videoToDelete.getId()+";";
        PreparedStatement st = connection.prepareStatement(query);
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

    public void update(Video videoToUpdate){
        //UPDATE movie SET name = ?..... WHERE id = '+ videoToUpdate.getId() +'
    }
}
