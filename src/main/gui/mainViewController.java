package main.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class mainViewController {
    public void handleAddCategory(ActionEvent actionEvent) {
        openNewWindow("newCategory/NewCategoryView.fxml");
    }

    public void handleRemoveCategory(ActionEvent actionEvent) {
    }

    public void handleAddMovie(ActionEvent actionEvent) {
        openNewWindow("newVideo/NewVideoView.fxml");
    }

    public void handleRemoveMovie(ActionEvent actionEvent) {

    }

    public void openNewWindow(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent mainLayout = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(mainLayout));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
