package main.dal;

import javafx.collections.ObservableList;
import main.be.Video;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Collection;

public class VideoRepository {

    private Connection connection;

    public VideoRepository() {
        //Create JDBC connection to MSSQL database here
    }

    public ObservableList<Video> loadVideos() {
        //SELECT * FROM movie - returns a list of all videos from table movie
        return null;
    }

    public void delete(Video videoToDelete){

        String query = "DELETE FROM MOVIE WHERE ID = ?;";
    }

    public void add(Video videoToAdd){
        //INSERT INTO movie - adds a video object to the table movie
        //Remember to fiddle with getGeneratedKeys() to get the Insert ID
    }

    public void update(Video videoToUpdate){
        //UPDATE movie SET name = ?..... WHERE id = '+ videoToUpdate.getId() +'
    }
}
