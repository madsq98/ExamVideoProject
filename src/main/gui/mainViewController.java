package main.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private static final String ERROR_TITLE = "An error occurred!";
    private static final String ERROR_HEADER = "A mistake was made!";
/*
        GUI objects
 */
    public ListView<Category> lstviewCategories;
    public TableView<Video> tblviewMovies;
    public TableColumn<Video,String> mvName;
    public TableColumn<Video,String> mvPath;
    public TableColumn<Video,Number> mvRating;
    public TableColumn<Video,LocalDate> mvLastSeen;
    public Button btnAddMovie;
    public Button btnRemoveMovie;

    /*
                Setting managers
    */
    private CategoryManager cMan;
    private VideoManager vMan;

/*
        Controls regarding functionality
 */
    private Category selectedCategory;
    private Video selectedVideo;

    /**
     * Initialize function, method called when GUI is loaded
     */
    public void initialize() {
        try {
            vMan = new VideoManager();
            cMan = new CategoryManager();
        } catch(SQLException e) {
            showError(e.getMessage());
        }

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
        mvLastSeen.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getLastViewProperty());

        lstviewCategories.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedCategory = newValue);

        tblviewMovies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedVideo = newValue);
    }

/*
        Category buttons
 */
    public void handleAddCategory(ActionEvent actionEvent) {
        openNewCategory("newCategory/NewCategoryView.fxml");
    }

    public void handleRemoveCategory(ActionEvent actionEvent) {
        if(selectedCategory != null) {
            try {
                cMan.delete(selectedCategory);
            } catch(SQLException e) {
                showError(e.getMessage());
            }
        }
        else {
            showError("You have to choose a category!");
        }
    }

    public void handleEditCategory(ActionEvent actionEvent) {
        if(selectedCategory != null) {
            openEditCategory("editCategory/EditCategoryView.fxml");
        }
        else {
            showError("You have to choose a category!");
        }
    }
/*
        Movie buttons
 */
    public void handleAddMovie(ActionEvent actionEvent) {
        openNewMovie("newVideo/NewVideoView.fxml");
    }

    public void handleRemoveMovie(ActionEvent actionEvent) {
        if(selectedVideo != null) {
            try {
                vMan.delete(selectedVideo);
            } catch(SQLException e) {
                showError(e.getMessage());
            }
        }
        else {
            showError("You have to choose a video!");
        }
    }

    public void handleEditMovie(ActionEvent actionEvent) {
        if(selectedVideo != null) {
            openEditMovie("editVideo/EditVideoView.fxml");
        }
        else {
            showError("You have to choose a video!");
        }
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
            showError(e.getMessage());
        }
    }

    public void openEditCategory(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            EditCategoryController cvc = loader.getController();
            cvc.setManager(this.cMan);
            cvc.setSelectedCategory(this.selectedCategory);
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            showError(e.getMessage());
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
            showError(e.getMessage());
        }
    }

    public void openEditMovie(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            EditVideoController cvc = loader.getController();
            cvc.setManager(this.vMan);
            cvc.setSelectedVideo(this.selectedVideo);
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            showError(e.getMessage());
        }
    }

    private void showError(String errorText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR_TITLE);
        alert.setHeaderText(ERROR_HEADER);
        alert.setContentText(errorText);
        alert.showAndWait();
    }

    public void handleAddToCat(ActionEvent actionEvent) {
        if(selectedCategory != null && selectedVideo != null) {
            selectedCategory.addVideo(selectedVideo);
        }
    }

    public void handleRemoveFromCat(ActionEvent actionEvent) {
        if(selectedCategory != null && selectedVideo != null) {
            selectedCategory.deleteVideo(selectedVideo);
        }
    }
}
