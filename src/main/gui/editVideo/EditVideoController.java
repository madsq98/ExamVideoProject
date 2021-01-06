package main.gui.editVideo;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import main.be.Video;
import main.bll.CategoryManager;
import main.bll.VideoManager;

public class EditVideoController {
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
