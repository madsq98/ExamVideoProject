package main.be;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Video {
    private IntegerProperty id;
    private StringProperty name;
    private DoubleProperty rating;
    private StringProperty path;
    private LocalDate lastView;

    public Video(String name, String path) {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.rating = new SimpleDoubleProperty();
        this.path = new SimpleStringProperty();
        setName(name);
        setPath(path);
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty getIdProperty() {
        return id;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public void setRating(double rating) {
        this.rating.set(rating);
    }

    public double getRating() {
        return rating.get();
    }

    public DoubleProperty getRatingProperty() {
        return rating;
    }

    public void setPath(String path) {
        this.path.set(path);
    }

    public String getPath() {
        return path.get();
    }

    public StringProperty getPathProperty() {
        return path;
    }

    public void setLastView(LocalDate lastView) {
        this.lastView = lastView;
    }

    public LocalDate getLastView() {
        return lastView;
    }
}