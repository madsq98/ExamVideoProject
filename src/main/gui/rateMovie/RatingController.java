package main.gui.rateMovie;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.be.Video;
import main.bll.VideoManager;
import main.util.UserError;

import java.sql.SQLException;

public class RatingController {
    public Button btnCancelRating;
    public Slider sliderRating;
    public Label lblRating;

    private VideoManager vMan;
    private Video selectedVideo;

    public void initialize() {
        sliderRating.setMin(0.0);
        sliderRating.setMax(10.0);
        sliderRating.setMajorTickUnit(0.5);
        sliderRating.setMinorTickCount(0);
        sliderRating.setBlockIncrement(0.5);
        sliderRating.setShowTickLabels(true);
        sliderRating.setShowTickMarks(true);
        sliderRating.setSnapToTicks(true);
        sliderRating.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                lblRating.setText(String.valueOf(sliderRating.getValue()));
            }
        });
    }
    

    public void handleCancel(ActionEvent actionEvent) {
        closeWin();
    }

    public void handleSaveRating(ActionEvent actionEvent) {
        Video newVideo = new Video(selectedVideo.getName(), selectedVideo.getPath());
        newVideo.setId(selectedVideo.getId());
        newVideo.setRating(sliderRating.getValue());
        newVideo.setLastView(selectedVideo.getLastView());

        try {
            vMan.replace(selectedVideo, newVideo);
            closeWin();
        } catch(SQLException e) {
            String errorHeader = "Something went wrong!";
            UserError.showError(errorHeader,e.getMessage());
        }

        selectedVideo.setRating(sliderRating.getValue());
        closeWin();

    }

    private void closeWin(){
        Stage stage = (Stage) btnCancelRating.getScene().getWindow();
        stage.close();
    }

    public void setManager(VideoManager vMan) {
        this.vMan = vMan;
    }

    public void setSelectedVideo(Video selectedVideo) {
        this.selectedVideo = selectedVideo;
    }


}
