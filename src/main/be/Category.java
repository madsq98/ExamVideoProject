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

    public Category(String name) {
        id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        videos = FXCollections.observableArrayList();
        setName(name);
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

    public void addVideo(Video v) {
        videos.add(v);
    }

    public void deleteVideo(Video v) {
        videos.remove(v);
    }
}
