package main.bll;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.be.Video;
import main.dal.VideoRepository;

import java.sql.SQLException;

public class VideoManager {
    private ObservableList<Video> videos;
    private VideoRepository vRepo;

    public VideoManager() throws SQLException {
        videos = FXCollections.observableArrayList();
        vRepo = new VideoRepository();
    }

    public void add(Video v) {
        videos.add(v);
    }

    public void delete(Video v) {
        videos.remove(v);
    }

    public void replace(Video s, Video r) {
        for(int i = 0; i < videos.size(); i++) {
            if(videos.get(i) == s) {
                videos.set(i,r);
            }
        }
    }

    public ObservableList<Video> search(String filter, ObservableList<Video> search) {
        return search;
    }

    public ObservableList<Video> getAllVideos() {
        return videos;
    }
}
