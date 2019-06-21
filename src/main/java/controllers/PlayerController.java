package controllers;

import client.PlayerClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {

    @FXML
    private ImageView play_pause;

    @FXML
    private ImageView imageMusic;

    @FXML
    private Slider sliderOfTheTrack;

    @FXML
    private Button browseButton;

    @FXML
    private MediaView mediaView;

    @FXML
    private Label nameOfTheTrack;

    @FXML
    private Slider volumeSlider;

    @FXML
    private ImageView volumeImage;


    private File mediaFile = null;

    private Media media = null;

    private MediaPlayer mediaPlayer = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectMediaFile();
    }

    @FXML
    private void selectMediaFile() {
        browseButton.setOnAction(event -> {
            // выбор медиафайла
            mediaFile = PlayerClient.chooseFile();

            // создаем плеер с даным файлом
            if (mediaFile != null){
                nameOfTheTrack.setText(mediaFile.getName());
                media = new Media(mediaFile.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);
            }
        });
    }
}

