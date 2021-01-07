package main.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.be.Category;
import main.be.Video;
import main.bll.CategoryManager;
import main.bll.VideoManager;
import main.gui.editCategory.EditCategoryController;
import main.gui.editVideo.EditVideoController;
import main.gui.newCategory.NewCategoryController;
import main.gui.newVideo.NewVideoController;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class mainViewController {
/*
        GUI objects
 */
    public ListView<Category> lstviewCategories;
    public TableView<Video> tblviewMovies;
    public TableColumn<Video,String> mvName;
    public TableColumn<Video,String> mvPath;
    public TableColumn<Video,Number> mvRating;
    public TableColumn<Video,LocalDate> mvLastSeen;

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

    /**
     * Initialize function, method called when GUI is loaded
     */
    public void initialize() {
        lstviewCategories.setItems(cMan.getCategories());

        double width = 750.0;
        mvName.setPrefWidth(width * 0.25);
        mvPath.setPrefWidth(width * 0.25);
        mvRating.setPrefWidth(width * 0.25);
        mvLastSeen.setPrefWidth(width * 0.25);

        tblviewMovies.setItems(vMan.getAllVideos());
        mvName.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getNameProperty());
        mvPath.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getPathProperty());
        mvRating.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getRatingProperty());

        lstviewCategories.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedCategory = newValue;
        });
    }

/*
        Category buttons
 */
    public void handleAddCategory(ActionEvent actionEvent) {
        openNewCategory("newCategory/NewCategoryView.fxml");
    }

    public void handleRemoveCategory(ActionEvent actionEvent) {
        if(selectedCategory != null) {
            cMan.delete(selectedCategory);
        }
    }

    public void handleEditCategory(ActionEvent actionEvent) {
        openEditCategory("editCategory/EditCategoryView.fxml");
    }
/*
        Movie buttons
 */
    public void handleAddMovie(ActionEvent actionEvent) {openNewMovie("newVideo/NewVideoView.fxml");}

    public void handleRemoveMovie(ActionEvent actionEvent) {

    }

    public void handleEditMovie(ActionEvent actionEvent) {openEditMovie("editVideo/EditVideoView.fxml");
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

    public void openEditCategory(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            EditCategoryController cvc = loader.getController();
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

    public void openEditMovie(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            EditVideoController cvc = loader.getController();
            cvc.setManager(this.vMan);
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
