package controllers;

import client.PlayerClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import start.Main;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {

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

    private File mediaFile = null;

    private Media media = null;

    private MediaPlayer mediaPlayer = null;

    @FXML
    private void selectMediaFile() {
        browseButton.setOnAction(event -> {
            Main.service.submit(() -> {
                mediaFile = PlayerClient.chooseFile();
                Platform.runLater(() -> {
                    nameOfTheTrack.setText(mediaFile.getName());
                    media = new Media(mediaFile.toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaView.setMediaPlayer(mediaPlayer);
                });
            });
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectMediaFile();
        //Main.service.submit(() -> YouTubeAnalytic.rotatePicture(imageView));
        //backToMain();
        // backToYoutubeAnalytic();
//        CompareInfo.show(searchButton, channelsIdField, tableView, nameColumn, dateColumn, subsColumn,
//                videoColumn, viewsColumn, showTime);
    }
}

