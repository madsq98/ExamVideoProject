package main.bll;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.be.Video;
import main.dal.VideoRepository;

import java.sql.SQLException;
import java.util.regex.Pattern;

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

    public boolean exists(Video v) throws SQLException {
        return vRepo.exists(v);
    }

    public ObservableList<Video> getAllVideos() {
        return videos;
    }

    public ObservableList<Video> search(String filter, ObservableList<Video> videos) {
        ObservableList<Video> returnList = FXCollections.observableArrayList();
        char[] filterChars = filter.toCharArray();
        String decimalPattern = "([0-9]*)\\.([0-9]*)";

        StringBuilder sb = new StringBuilder();
        for (char c: filterChars) {
            if(Character.isDigit(c)) {
                sb.append(c);
                }
            }

        for(Video v : videos) {
            if(v.getName().toLowerCase().contains(filter.toLowerCase()) || String.valueOf(v.getRating()).contains(filter.toLowerCase())) {
                if(!returnList.contains(v)) {
                    returnList.add(v);
                }
            }
            if (!sb.toString().isBlank()) {
                if (Pattern.matches(decimalPattern, filter)) {
                    double searchValue = Double.parseDouble(filter);
                    if (searchValue == v.getRating()) {
                        if(!returnList.contains(v)) {
                            returnList.add(v);
                        }
                    }
                }
                if(!Pattern.matches(decimalPattern,filter))
                for (char c : sb.toString().toCharArray()) {
                    if (Character.isDigit(c)) {
                        if (String.valueOf(v.getRating()).contains(String.valueOf(c))) {
                            if(!returnList.contains(v)) {
                                returnList.add(v);
                            }
                        }
                    }
                }
            }
            }

        return returnList;
    }
}
