package main.be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Category {
    private IntegerProperty id;
    private StringProperty name;
    private ObservableList<Video> videos;

    /**
     * Constructor for setting up a category
     * @param name Name of category
     */
    public Category(String name) {
        id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        videos = FXCollections.observableArrayList();
        setName(name);
    }

    /**
     * Sets ID of category
     * @param id id to set
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * Gets ID of category
     * @return id
     */
    public int getId() {
        return id.get();
    }

    /**
     * Gets ID Property of category, for use in JavaFX
     * @return id property
     */
    public IntegerProperty getIdProperty() {
        return id;
    }

    /**
     * Sets Name of category
     * @param name name to set
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Gets name of category
     * @return name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Gets name property of category, for use in JavaFX
     * @return name property
     */
    public StringProperty getNameProperty() {
        return name;
    }

    /**
     * Adds video to list videos
     * @param v video to add
     */
    public void addVideo(Video v) {
        videos.add(v);
    }

    /**
     * Removes video from list videos
     * @param v video to delete
     */
    public void deleteVideo(Video v) {
        videos.remove(v);
    }

    /**
     * Adds multiple videos to list videos
     * @param v videos to add
     */
    public void addVideos(ObservableList<Video> v) {
        videos.addAll(v);
    }

    /**
     * Gets list of videos
     * @return videos
     */
    public ObservableList<Video> getVideos() {
        return videos;
    }

    /**
     * Used for formatting text output in listview
     * @return name of category
     */
    @Override
    public String toString() {
        return getName();
    }
}
