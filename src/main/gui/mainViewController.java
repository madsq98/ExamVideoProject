package main.gui;

import com.sun.javafx.iio.ios.IosDescriptor;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.be.Category;
import main.be.Video;
import main.bll.CategoryManager;
import main.bll.VideoManager;
import main.gui.editCategory.EditCategoryController;
import main.gui.editVideo.EditVideoController;
import main.gui.newCategory.NewCategoryController;
import main.gui.newVideo.NewVideoController;
import main.gui.rateMovie.RatingController;
import main.util.UserError;

import java.awt.*;
import java.io.File;
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

    public TableView<Video> tblviewMoviesInCategory;
    public TableColumn<Video,String> catMvName;
    public TableColumn<Video,String> catMvPath;
    public TableColumn<Video,Number> catMvRating;
    public TableColumn<Video,LocalDate> catMvLastSeen;
    public Label categoryLabel;

    public Button btnAddMovie;
    public Button btnRemoveMovie;
    public TextField txtFieldSearch;

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

    public mainViewController() throws SQLException {
    }

    /**
     * Initialize function, method called when GUI is loaded
     */
    public void initialize() {
        try {
            vMan = new VideoManager();
            cMan = new CategoryManager();
        } catch (SQLException e) {
            showError(e.getMessage());
        }

        //Check for old videos
        ObservableList<Video> oldVideos = vMan.getOldVideos();
        if (!oldVideos.isEmpty()) {
            String warningText = "Remember to delete old/low-ranked videos! These videos are over 2 years old, and with a ranking below 6.0:\n";
            for (Video v : oldVideos) {
                warningText += v.getName() + "\n";
            }

            showWarning(warningText);
        }

        //Sets items of list view for categories
        lstviewCategories.setItems(cMan.getCategories());

        //Sets width of table view columns
        double width = 750.0;
        mvName.setPrefWidth(width * 0.25);
        mvPath.setPrefWidth(width * 0.25);
        mvRating.setPrefWidth(width * 0.25);
        mvLastSeen.setPrefWidth(width * 0.25);
        catMvName.setPrefWidth(width * 0.25);
        catMvPath.setPrefWidth(width * 0.25);
        catMvRating.setPrefWidth(width * 0.25);
        catMvLastSeen.setPrefWidth(width * 0.25);

        //Set items and columns for table view for all movies
        tblviewMovies.setItems(vMan.getAllVideos());
        mvName.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getNameProperty());
        mvPath.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getPathProperty());
        mvRating.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getRatingProperty());
        mvLastSeen.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getLastViewProperty());

        //Set columns for table view for movies in category (items are set in listener below)
        catMvName.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getNameProperty());
        catMvPath.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getPathProperty());
        catMvRating.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getRatingProperty());
        catMvLastSeen.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getLastViewProperty());

        //Listener for selected category
        lstviewCategories.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedCategory = newValue;
            if (selectedCategory != null) {
                if (selectedCategory.getId() != -1) {
                    categoryLabel.setText("Category: " + selectedCategory.getName());
                    tblviewMoviesInCategory.setItems(selectedCategory.getVideos());
                } else {
                    categoryLabel.setText("Category: NONE SELECTED");
                    tblviewMoviesInCategory.setItems(null);
                }
            } else {
                tblviewMoviesInCategory.setItems(null);
                categoryLabel.setText("Category: NONE SELECTED");
            }
        });

        //Listener for selected movie in all movies
        tblviewMovies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedVideo = newValue);

        //Listener for selected movie in category movies
        tblviewMoviesInCategory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedVideo = newValue);

        //Listener for double click on all movies
        tblviewMovies.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                showMovie(tblviewMovies.getSelectionModel().getSelectedItem());
            }
        });

        //Listener for double click on category movies
        tblviewMoviesInCategory.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                showMovie(tblviewMoviesInCategory.getSelectionModel().getSelectedItem());
            }
        });
        filterListener();
    }

    /**
     * Function for opening movie
     * @param v video to open
     */
    private void showMovie(Video v) {
        try {
            String path = v.getPath();
            File f = new File(path);
            if(f.isFile() && !f.isDirectory()) {
                Video newVideo = v;
                v.setLastView(LocalDate.now());
                try {
                    vMan.replace(v, newVideo);
                    Desktop.getDesktop().open(f);
                } catch(SQLException e) {
                    showError(e.getMessage());
                }
            } else {
                showError("File '" + path + "' does not exist!");
            }
        } catch(IOException e) {
            showError(e.getMessage());
        }
    }

    /**
     * Button click on add category
     * @param actionEvent
     */
    public void handleAddCategory(ActionEvent actionEvent) {
        openNewCategory("newCategory/NewCategoryView.fxml");
    }

    /**
     * Button click on delete category
     * @param actionEvent
     */
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

    /**
     * Button click on edit category
     * @param actionEvent
     */
    public void handleEditCategory(ActionEvent actionEvent) {
        if(selectedCategory != null) {
            openEditCategory("editCategory/EditCategoryView.fxml");
        }
        else {
            showError("You have to choose a category!");
        }
    }

    /**
     * Button click on add movie
     * @param actionEvent
     */
    public void handleAddMovie(ActionEvent actionEvent) {
        openNewMovie("newVideo/NewVideoView.fxml");
    }

    /**
     * Button click on delete movie
     * @param actionEvent
     */
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

    /**
     * Button click on edit movie
     * @param actionEvent
     */
    public void handleEditMovie(ActionEvent actionEvent) {
        if(selectedVideo != null) {
            openEditMovie("editVideo/EditVideoView.fxml");
        }
        else {
            showError("You have to choose a video!");
        }
    }

    /**
     * Button click on rate movie
     * @param actionEvent
     */
    public void handleRateMovie(ActionEvent actionEvent) {
        if(selectedVideo != null) {
            openRateMovie("rateMovie/RatingView.fxml");
        }
        else {
            showError("You have to choose a video!");
        }
    }

    /**
     * Button click on add to category
     * @param actionEvent
     */
    public void handleAddToCat(ActionEvent actionEvent) {
        if(selectedCategory != null && selectedVideo != null) {
            try {
                cMan.saveLink(selectedCategory, selectedVideo);
            } catch(SQLException e) {
                UserError.showError(ERROR_HEADER,e.getMessage());
            }
        }
    }

    /**
     * Button click on remove from category
     * @param actionEvent
     */
    public void handleRemoveFromCat(ActionEvent actionEvent) {
        if(selectedCategory != null && selectedVideo != null) {
            try {
                cMan.deleteLink(selectedCategory,selectedVideo);
            } catch(SQLException e) {
                UserError.showError(ERROR_HEADER,e.getMessage());
            }
        }
    }

    /**
     * Opens the new category window
     * @param fxmlPath file path to FXML document
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

    /**
     * Opens the edit category window
     * @param fxmlPath file path to FXML document
     */
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

    /**
     * Opens the new movie window
     * @param fxmlPath file path to FXML document
     */
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

    /**
     * Opens the edit movie window
     * @param fxmlPath file path to FXML document
     */
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

    /**
     * Opens the rate movie window
     * @param fxmlPath file path to FXML document
     */

    public void openRateMovie(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            RatingController cvc = loader.getController();
            cvc.setManager(this.vMan);
            cvc.setSelectedVideo(this.selectedVideo);
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            showError(e.getMessage());
        }
    }

    /**
     * Private function for showing error
     * @param errorText
     */
    private void showError(String errorText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR_TITLE);
        alert.setHeaderText(ERROR_HEADER);
        alert.setContentText(errorText);
        alert.showAndWait();
    }

    /**
     * Private function for showing warning
     * @param errorText
     */
    private void showWarning(String errorText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(ERROR_TITLE);
        alert.setHeaderText(ERROR_HEADER);
        alert.setContentText(errorText);
        alert.showAndWait();
    }

    /**
     * Search function in main view
     */
    private void filterListener() {
        this.txtFieldSearch.textProperty().addListener((observable,oldValue,newValue) -> {
            if(!newValue.isEmpty() && !newValue.isBlank()) {
                tblviewMovies.setItems(vMan.search(txtFieldSearch.getText(),vMan.getAllVideos()));
                if(selectedCategory != null) {
                    tblviewMoviesInCategory.setItems(vMan.search(txtFieldSearch.getText(),selectedCategory.getVideos()));
                }
            }
            else {
                tblviewMovies.setItems(vMan.getAllVideos());
                if(selectedCategory != null) {
                    tblviewMoviesInCategory.setItems(selectedCategory.getVideos());
                }
            }
        });
    }
}
