package main.gui.newVideo;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.be.Video;
import main.bll.VideoManager;
import main.util.UserError;

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
        String errorHeader = "Something went wrong!";
        String title = txtVideoTitle.getText();
        String filePath = txtVideoFile.getText();
        if(title.isEmpty()) {
            UserError.showError(errorHeader,"Please provide a title!");
            return;
        }
        if(filePath.isEmpty()) {
            UserError.showError(errorHeader,"Please provide file path!");
            return;
        }
        if(!filePath.toLowerCase().endsWith(".mp4")){
            UserError.showError(errorHeader,"Please provide an mp4 file!");
            return;
        }

        Video newVideo = new Video(title, filePath);

        this.vMan.add(newVideo);
        this.closeWin();
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


