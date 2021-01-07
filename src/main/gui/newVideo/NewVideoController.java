package main.gui.newVideo;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.bll.VideoManager;
import java.io.File;


public class NewVideoController {
    public TextField txtVideoTitle;
    public TextField txtVideoFile;
    public Button btnCancelNewVideo;


    private VideoManager vMan;

    public void handleCancel(ActionEvent actionEvent) {
        closeWin();
    }

    public void handleSave(ActionEvent actionEvent) {
    }

    public void setManager(VideoManager vMan) {this.vMan = vMan;}

    public void handleChooseVideoFile(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) btnCancelNewVideo.getScene().getWindow();
            FileChooser fc = new FileChooser();
            fc.setTitle("Choose Video...");
            File file = fc.showOpenDialog(stage);
            String filePath = file.toString().replaceAll("\\\\","/");
            txtVideoFile.setText(filePath);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void closeWin(){
        Stage stage = (Stage) btnCancelNewVideo.getScene().getWindow();
        stage.close();
    }
}


