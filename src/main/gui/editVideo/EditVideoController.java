package main.gui.editVideo;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.be.Video;
import main.bll.CategoryManager;

import main.bll.VideoManager;

public class EditVideoController {
    public TextField txtVideoTitle;
    public TextField txtVideoGenre;
    public TextField txtVideoRating;
    public TextField txtVideoFile;
    public Button btnCancelEditVideo;

    private VideoManager vMan;

    public void handleChooseVideoFile(ActionEvent actionEvent) {
    }

    public void handleCancel(ActionEvent actionEvent) {
        closeWin();
    }

    public void handleSave(ActionEvent actionEvent) {
    }

    public void setManager(VideoManager vMan) {this.vMan = vMan;}

    private void closeWin(){
        Stage stage = (Stage) btnCancelEditVideo.getScene().getWindow();
        stage.close();


    }
}
