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

    /**
     * Constructor for Category Manager
     * @throws SQLException SQL Error
     */
    public CategoryManager() throws SQLException {
        categories = FXCollections.observableArrayList();
        cRepo = new CategoryRepository();

        categories.addAll(cRepo.loadCategories());
    }

    /**
     * Adds category to memory
     * @param c category to add
     * @throws SQLException SQL Error
     */
    public void add(Category c) throws SQLException {
        int newId = cRepo.add(c);
        c.setId(newId);
        categories.add(c);
    }

    /**
     * Deletes category from memory
     * @param c category to delete
     * @throws SQLException SQL Error
     */
    public void delete(Category c) throws SQLException {
        cRepo.delete(c);
        categories.remove(c);
    }

    /**
     * Replaces categories in memory
     * @param s category to be replaced
     * @param r category to replace
     * @throws SQLException SQL Error
     */
    public void replace(Category s, Category r) throws SQLException {
        cRepo.update(r);
        for(int i = 0; i < categories.size(); i++) {
            if(categories.get(i) == s) {
                categories.set(i,r);
            }
        }
    }

    /**
     * Saves a link between video and category
     * @param c selected category
     * @param v selected video
     * @throws SQLException SQL Error
     */
    public void saveLink(Category c, Video v) throws SQLException {
        cRepo.saveLink(c,v);
        c.addVideo(v);
    }

    /**
     * Deletes link between video and category
     * @param c selected category
     * @param v selected video
     * @throws SQLException SQL Error
     */
    public void deleteLink(Category c, Video v) throws SQLException {
        cRepo.deleteLink(c,v);
        c.deleteVideo(v);
    }

    /**
     * Gets a list of all categories
     * @return list of categories
     */
    public ObservableList<Category> getCategories() {
        return categories;
    }
}
