package main.gui.newVideo;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import main.bll.VideoManager;

public class NewVideoController {
    public TextField txtVideoTitle;
    public TextField txtVideoGenre;
    public TextField txtVideoRating;
    public TextField txtVideoFile;
    private VideoManager vMan;

    public void handleChooseVideoFile(ActionEvent actionEvent) {
    }

    public void handleCancel(ActionEvent actionEvent) {
    }

    public void handleSave(ActionEvent actionEvent) {
    }

    public void setManager(VideoManager vMan) {
        this.vMan = vMan;
    }
}
