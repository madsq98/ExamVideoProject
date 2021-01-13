package main.bll;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.be.Video;
import main.dal.VideoRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.DAYS;

public class VideoManager {
    private ObservableList<Video> videos;
    private VideoRepository vRepo;

    /**
     * Constructor for Video Manager
     * @throws SQLException SQL Error
     */
    public VideoManager() throws SQLException {
        videos = FXCollections.observableArrayList();
        vRepo = new VideoRepository();

        videos.addAll(vRepo.loadVideos());
    }

    /**
     * Adds video to memory
     * @param v video to add
     * @throws SQLException SQL Error
     */
    public void add(Video v) throws SQLException {
        int newId = vRepo.add(v);
        v.setId(newId);
        videos.add(v);
    }

    /**
     * Deletes video from memory
     * @param v video to delete
     * @throws SQLException SQL Error
     */
    public void delete(Video v) throws SQLException {
        vRepo.delete(v);
        videos.remove(v);
    }

    /**
     * Replaces videos in memory
     * @param s video to be replaced
     * @param r video to replace
     * @throws SQLException SQL Error
     */
    public void replace(Video s, Video r) throws SQLException {
        vRepo.update(r);
        for(int i = 0; i < videos.size(); i++) {
            if(videos.get(i) == s) {
                videos.set(i,r);
            }
        }
    }

    /**
     * Check if video title/path exists in database
     * @param v video to check
     * @return true if exists, false if not
     * @throws SQLException SQL Error
     */
    public boolean exists(Video v) throws SQLException {
        return vRepo.exists(v);
    }

    /**
     * Gets list of all videos
     * @return list of videos
     */
    public ObservableList<Video> getAllVideos() {
        return videos;
    }

    /**
     * Gets list of old & low-rated videos
     * @return list of videos
     */
    public ObservableList<Video> getOldVideos() {
        ObservableList<Video> returnList = FXCollections.observableArrayList();
        int daysToBeOld = (2 * 365);
        int lowRating = 6;

        for(Video v : videos) {
            LocalDate lastSeen = v.getLastView();
            LocalDate today = LocalDate.now();
            double rating = v.getRating();
            int daysBetween = (int) DAYS.between(lastSeen,today);

            if(daysBetween > daysToBeOld && rating < lowRating) {
                returnList.add(v);
            }
        }

        return returnList;
    }

    /**
     * Filter function for searching
     * @param filter chosen filter
     * @param videos list to search in
     * @return list of results
     */
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
