package main.dal;

import javafx.collections.ObservableList;
import main.be.Video;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Collection;

public class VideoRepository {

    private Connection connection;


    public ObservableList<Video> loadVideos(){
        return null;
    }

    public void delete(Video videoToDelete){

    }

    public void add(Video videoToAdd){

    }

    public void update(Video videoToUpdate, String name, Double rating, String path, LocalDate lastView){

    }
}
