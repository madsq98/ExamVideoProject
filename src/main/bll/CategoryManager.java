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

        categories.addAll(cRepo.loadCategories());
    }

    public void add(Category c) throws SQLException {
        int newId = cRepo.add(c);
        c.setId(newId);
        categories.add(c);
    }

    public void delete(Category c) throws SQLException {
        cRepo.delete(c);
        categories.remove(c);
    }

    public void replace(Category s, Category r) throws SQLException {
        cRepo.update(r);
        for(int i = 0; i < categories.size(); i++) {
            if(categories.get(i) == s) {
                categories.set(i,r);
            }
        }
    }

    public void saveLink(Category c, Video v) throws SQLException {
        cRepo.saveLink(c,v);
        c.addVideo(v);
    }

    public void deleteLink(Category c, Video v) throws SQLException {
        cRepo.deleteLink(c,v);
        c.deleteVideo(v);
    }

    public void saveLinks(Category c) throws SQLException {
        //TODO
    }

    public ObservableList<Category> getCategories() {
        return categories;
    }
}
