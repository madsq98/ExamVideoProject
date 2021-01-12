package main.be;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Video {
    private IntegerProperty id;
    private StringProperty name;
    private DoubleProperty rating;
    private StringProperty path;
    private ObjectProperty<LocalDate> lastView;

    /**
     * Constructor for setting up a Video
     * @param name name of video
     * @param path path to video
     */
    public Video(String name, String path) {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.rating = new SimpleDoubleProperty();
        this.path = new SimpleStringProperty();
        this.lastView = new SimpleObjectProperty<LocalDate>();
        setName(name);
        setPath(path);
        setRating(0.0);
        setLastView(LocalDate.now());
    }

    /**
     * Sets id of video
     * @param id id to set
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * Gets id of video
     * @return id of video
     */
    public int getId() {
        return id.get();
    }

    /**
     * Gets id property of video, for use in JavaFX
     * @return id property
     */
    public IntegerProperty getIdProperty() {
        return id;
    }

    /**
     * Sets name of video
     * @param name name to set
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Gets name of video
     * @return name of video
     */
    public String getName() {
        return name.get();
    }

    /**
     * Gets name property of video, for use in JavaFX
     * @return name property
     */
    public StringProperty getNameProperty() {
        return name;
    }

    /**
     * Sets rating of video
     * @param rating rating to set
     */
    public void setRating(double rating) {
        this.rating.set(rating);
    }

    /**
     * Gets rating of video
     * @return rating of video
     */
    public double getRating() {
        return rating.get();
    }

    /**
     * Gets rating property of video, for use in JavaFX
     * @return rating property
     */
    public DoubleProperty getRatingProperty() {
        return rating;
    }

    /**
     * Sets path of video
     * @param path path to video
     */
    public void setPath(String path) {
        this.path.set(path);
    }

    /**
     * Gets path of video
     * @return path to video
     */
    public String getPath() {
        return path.get();
    }

    /**
     * Gets path property of video, for use in JavaFX
     * @return path property
     */
    public StringProperty getPathProperty() {
        return path;
    }

    /**
     * Sets last view of video
     * @param lastView date to set
     */
    public void setLastView(LocalDate lastView) {
        this.lastView.set(lastView);
    }

    /**
     * Gets last view of video
     * @return last view date
     */
    public LocalDate getLastView() {
        return lastView.get();
    }

    /**
     * Gets last view property of video, for use in JavaFX
     * @return last view property
     */
    public ObjectProperty<LocalDate> getLastViewProperty() {
        return lastView;
    }
}
