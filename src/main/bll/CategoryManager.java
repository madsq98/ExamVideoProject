package main.bll;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.be.Category;
import main.be.Video;
import main.dal.CategoryRepository;

import java.sql.SQLException;

public class CategoryManager {
    private ObservableList<Category> categories;
    private CategoryRepository cRepo;

    public CategoryManager() throws SQLException {
        categories = FXCollections.observableArrayList();
        cRepo = new CategoryRepository();

        Category c1 = new Category("Test 1");
        Category c2 = new Category("Test 2");

        categories.add(c1);
        categories.add(c2);
    }

    public void add(Category c) {
        categories.add(c);
    }

    public void delete(Category c) {
        categories.remove(c);
    }

    public void replace(Category s, Category r) {
        for(int i = 0; i < categories.size(); i++) {
            if(categories.get(i) == s) {
                categories.set(i,r);
            }
        }
    }

    public void saveLink(Category c, Video v) {
        c.addVideo(v);
    }

    public void deleteLink(Category c, Video v) {
        c.deleteVideo(v);
    }

    public void saveLinks(Category c) {
        //TODO
    }

    public ObservableList<Category> getCategories() {
        return categories;
    }
}
