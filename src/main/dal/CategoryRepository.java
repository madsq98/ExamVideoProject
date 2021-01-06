package main.dal;

import javafx.collections.ObservableList;
import main.be.Category;
import main.be.Video;

import java.sql.Connection;

public class CategoryRepository {

    private Connection connection;


    public ObservableList<Category> loadCategories(){


        return null;
    }

    public void delete(Category categoryToDelete){

    }

    public void add(Category categoryToAdd){

    }

    public void update(Category categoryToUpdate, String name, ObservableList videos){

    }

    public void saveAllLinks(Category c){

    }

    public void ObservableList(Category c){

    }

    public void saveLink(Category c, Video v){

    }

    public void deleteLink(Category c,Video v){

    }
}
