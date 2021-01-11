package main.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import static java.time.temporal.ChronoUnit.DAYS;

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
        } catch(SQLException e) {
            showError(e.getMessage());
        }

        lstviewCategories.setItems(cMan.getCategories());

        setTableViewWidth();
        initMainTableView();
        initSecondaryTableView();
        categoryListener();
        tableViewListeners();
        filterListener();

        oldVideosWarning();

        doubleClickListeners();
    }

    private void setTableViewWidth() {
        double width = 750.0;
        mvName.setPrefWidth(width * 0.25);
        mvPath.setPrefWidth(width * 0.25);
        mvRating.setPrefWidth(width * 0.25);
        mvLastSeen.setPrefWidth(width * 0.25);
        catMvName.setPrefWidth(width * 0.25);
        catMvPath.setPrefWidth(width * 0.25);
        catMvRating.setPrefWidth(width * 0.25);
        catMvLastSeen.setPrefWidth(width * 0.25);
    }

    private void initMainTableView() {
        tblviewMovies.setItems(vMan.getAllVideos());
        mvName.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getNameProperty());
        mvPath.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getPathProperty());
        mvRating.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getRatingProperty());
        mvLastSeen.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getLastViewProperty());
    }

    private void initSecondaryTableView() {
        catMvName.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getNameProperty());
        catMvPath.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getPathProperty());
        catMvRating.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getRatingProperty());
        catMvLastSeen.cellValueFactoryProperty().setValue(cellData -> cellData.getValue().getLastViewProperty());
    }

    private void categoryListener() {
        lstviewCategories.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.getId() != -1) {
                selectedCategory = newValue;
                tblviewMoviesInCategory.setItems(selectedCategory.getVideos());
                categoryLabel.setText("Category: " + selectedCategory.getName());
            } else {
                selectedCategory = null;
                tblviewMoviesInCategory.setItems(null);
                categoryLabel.setText("Category: NONE SELECTED");
            }
        });
    }

    private void tableViewListeners() {
        tblviewMovies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedVideo = newValue);

        tblviewMoviesInCategory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedVideo = newValue);
    }

    private void doubleClickListeners() {
        tblviewMovies.setOnMouseClicked((MouseEvent event) -> {
           if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
               String path = tblviewMovies.getSelectionModel().getSelectedItem().getPath();
               Video v = tblviewMovies.getSelectionModel().getSelectedItem();
               v.setLastView(LocalDate.now());

               try {
                   vMan.replace(tblviewMovies.getSelectionModel().getSelectedItem(), v);
               } catch(SQLException e) {
                   showError(e.getMessage());
               }

               openVideoFile(path);
           }
        });

        tblviewMoviesInCategory.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                String path = tblviewMoviesInCategory.getSelectionModel().getSelectedItem().getPath();
                Video v = tblviewMoviesInCategory.getSelectionModel().getSelectedItem();
                v.setLastView(LocalDate.now());

                try {
                    vMan.replace(tblviewMoviesInCategory.getSelectionModel().getSelectedItem(), v);
                } catch(SQLException e) {
                    showError(e.getMessage());
                }

                openVideoFile(path);
            }
        });
    }

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

    private void oldVideosWarning() {
        ObservableList<Video> oldVideos = FXCollections.observableArrayList();
        for(Video v : vMan.getAllVideos()) {
            int diff = (int) DAYS.between(v.getLastView(),LocalDate.now());
            if(diff > 730) {
                if(v.getRating() < 6.0) {
                    oldVideos.add(v);
                }
            }
        }

        if(!oldVideos.isEmpty()) {
            String oldVideosText = "Warning! Remember to delete old/unwanted videos. These videos have not been seen for 2 years, and has a low rating:\n";
            for(Video v : oldVideos) {
                oldVideosText += v.getName() + "\n";
            }

            showError(oldVideosText);
        }
    }

    private void openVideoFile(String path) {
        File f = new File(path);
        if(f.isFile() && !f.isDirectory()) {
            try {
                Desktop.getDesktop().open(f);
            } catch(IOException e) {
                showError(e.getMessage());
            }
        } else {
            showError("File with path: '"+path+"' does not exist");
        }
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

    public void handleRateMovie(ActionEvent actionEvent) {
        if(selectedVideo != null) {
            openRateMovie("rateMovie/RatingView.fxml");
        }
        else {
            showError("You have to choose a video!");
        }
    }

/*
        Methods to open Views
 */

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

    private void showError(String errorText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR_TITLE);
        alert.setHeaderText(ERROR_HEADER);
        alert.setContentText(errorText);
        alert.showAndWait();
    }

    public void handleAddToCat(ActionEvent actionEvent) {
        if(selectedCategory != null && selectedVideo != null) {
            try {
                cMan.saveLink(selectedCategory, selectedVideo);
            } catch(SQLException e) {
                UserError.showError(ERROR_HEADER,e.getMessage());
            }
        }
    }

    public void handleRemoveFromCat(ActionEvent actionEvent) {
        if(selectedCategory != null && selectedVideo != null) {
            try {
                cMan.deleteLink(selectedCategory,selectedVideo);
            } catch(SQLException e) {
                UserError.showError(ERROR_HEADER,e.getMessage());
            }
        }
    }
}
