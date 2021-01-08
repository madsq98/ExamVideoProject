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

        videos.addAll(vRepo.loadVideos());
    }

    public void add(Video v) throws SQLException {
        int newId = vRepo.add(v);
        v.setId(newId);
        videos.add(v);
    }

    public void delete(Video v) throws SQLException {
        vRepo.delete(v);
        videos.remove(v);
    }

    public void replace(Video s, Video r) throws SQLException {
        vRepo.update(r);
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
