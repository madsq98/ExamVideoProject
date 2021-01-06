package main.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import main.be.Category;
import main.be.Video;
import main.bll.CategoryManager;
import main.bll.VideoManager;
import main.gui.newCategory.NewCategoryController;
import main.gui.newVideo.NewVideoController;

import java.io.IOException;

public class mainViewController {
/*
        GUI objects
 */
    public ListView lstviewCategories;

/*
            Setting managers
*/
    private CategoryManager cMan = new CategoryManager();
    private VideoManager vMan = new VideoManager();

/*
        Controls regarding functionality
 */
    private Category selectedCategory;
    private Video selectedVideo;

/*
        Category buttons
 */
    public void handleAddCategory(ActionEvent actionEvent) {openNewCategory("newCategory/NewCategoryView.fxml");}

    public void handleRemoveCategory(ActionEvent actionEvent) {
        
    }

/*
        Movie buttons
 */
    public void handleAddMovie(ActionEvent actionEvent) {openNewMovie("newVideo/NewVideoView.fxml");}

    public void handleRemoveMovie(ActionEvent actionEvent) {

    }

/*
        Methods to open Views
 */
    public void openNewCategory(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            NewCategoryController cvc = loader.getController();
            cvc.setManager(this.cMan);
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openNewMovie(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            NewVideoController cvc = loader.getController();
            cvc.setManager(this.vMan);
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
