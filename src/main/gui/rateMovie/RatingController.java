package main.gui.rateMovie;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.be.Video;
import main.bll.VideoManager;

public class RatingController {
    public Button btnCancelRating;
    public TextField txtFieldRating;
    public Slider sliderRating;

    private VideoManager vMan;
    private Video selectedVideo;

    public void initialize() {
        sliderRating.setMin(0.0);
        sliderRating.setMax(10.0);
        sliderRating.setMajorTickUnit(50);
        sliderRating.setMinorTickCount(1);
        sliderRating.setBlockIncrement(0.5);
        sliderRating.setShowTickLabels(true);
        sliderRating.setShowTickMarks(true);
        sliderRating.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                txtFieldRating.setText(String.valueOf(sliderRating.getValue()));
            }
        });
    }
    

    public void handleCancel(ActionEvent actionEvent) {
        closeWin();
    }

    public void handleSaveRating(ActionEvent actionEvent) {
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

    public void handleRatingtxtInput(KeyEvent keyEvent) {
    }
}
