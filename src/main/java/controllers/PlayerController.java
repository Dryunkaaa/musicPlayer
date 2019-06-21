package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaView;

public class PlayerController {

    @FXML
    private ImageView play_pause;

    @FXML
    private ImageView imageMuzic;

    @FXML
    private Slider sliderOfTheTrack;

    @FXML
    private ProgressBar volumeBar;

    @FXML
    private Button browseButton;

    @FXML
    private MediaView mediaView;

    @FXML
    private Label nameOfTheTrack;

}

