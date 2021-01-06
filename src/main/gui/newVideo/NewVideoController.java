package main.gui.newVideo;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.bll.VideoManager;

public class NewVideoController {
    public TextField txtVideoTitle;
    public TextField txtVideoGenre;
    public TextField txtVideoRating;
    public TextField txtVideoFile;
    public Button btnCancelNewVideo;

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
        Stage stage = (Stage) btnCancelNewVideo.getScene().getWindow();
        stage.close();
    }
}


