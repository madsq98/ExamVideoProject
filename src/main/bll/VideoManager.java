package main.bll;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.be.Video;
import main.dal.VideoRepository;

public class VideoManager {
    private ObservableList<Video> videos;
    private VideoRepository vRepo;

    public VideoManager() {
        videos = FXCollections.observableArrayList();
        vRepo = new VideoRepository();

        Video v1 = new Video("Terminator","C:/film/Terminator.mp4");
        Video v2 = new Video("Sigurds Bjørnetime","C:/film/Sigurd.mp4");

        videos.add(v1);
        videos.add(v2);
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
