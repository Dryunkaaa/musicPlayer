package controllers;

import client.PlayerClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import start.Main;

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

    private boolean start = false;

    private File mediaFile = null;

    private Media media = null;

    private MediaPlayer mediaPlayer = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectMediaFile();
        setEventToPauseStart();
        setActionsToSliderOfTheTrack();
        setActionsToVolumeSlider();
    }

    @FXML
    private void selectMediaFile() {
        browseButton.setOnAction(event -> {
            // выбор медиафайла
            mediaFile = PlayerClient.chooseFile();
            // создаем плеер с даным файлом
            if (mediaFile != null) {
                nameOfTheTrack.setText(mediaFile.getName());
                media = new Media(mediaFile.toURI().toString());
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                mediaPlayer = new MediaPlayer(media);

                // указание длинны слайдера трека
                mediaPlayer.setOnReady(() -> {
                    setParametersToSliderOfTheTrack();
                });

                // движение слайдера трека
                mediaPlayer.setOnPlaying(() -> {
                    Main.service.submit(() -> {
                        while (start) {
                            sliderOfTheTrack.setValue(mediaPlayer.getCurrentTime().toSeconds());
                        }
                    });
                });

                volumeSlider.setValue(mediaPlayer.getVolume() * 100);
                mediaView.setMediaPlayer(mediaPlayer);
            }
        });
    }

    // задание максимума по длинне трека в сикундах
    private void setParametersToSliderOfTheTrack() {
        sliderOfTheTrack.setMin(0);
        sliderOfTheTrack.setMax(PlayerClient.getDurationOfTheFileS(media));
        sliderOfTheTrack.setValue(0);
    }

    @FXML
    private void setEventToPauseStart() {
        play_pause.setOnMouseClicked(event -> {
            startOrPause();
        });
    }

    // пауза\запуск медиафайла
    private void startOrPause() {
        if (start) mediaPlayer.pause();
        else mediaPlayer.play();
        changeImagePause();
        start = !start;
    }

    // смена картинки паузы
    private void changeImagePause() {
        if (start) {
            File file = new File("C:\\Users\\Andrey\\IDEA\\MusicPlayer\\src\\main\\resources\\icons\\-play.png");
            play_pause.setImage(new Image(file.toURI().toString()));
        } else {
            File file = new File("C:\\Users\\Andrey\\IDEA\\MusicPlayer\\src\\main\\resources\\icons\\pause.png");
            play_pause.setImage(new Image(file.toURI().toString()));
        }
    }

    //измененить время проигрывания слайдером
    @FXML
    private void setActionsToSliderOfTheTrack() {
        sliderOfTheTrack.setOnMouseDragged(event -> {
            eventToSliderOfTheTrack();
        });

        sliderOfTheTrack.setOnMouseClicked(event -> {
            eventToSliderOfTheTrack();
        });
    }

    //событие слайдера трека
    private void eventToSliderOfTheTrack() {
        start = false;
        mediaPlayer.pause();
        double value = sliderOfTheTrack.getValue();
        Duration duration = Duration.seconds(value);
        mediaPlayer.setStartTime(duration);
        mediaPlayer.stop();
        mediaPlayer.play();
        start = true;
    }


    // изменение громкости на плеере
    @FXML
    private void setActionsToVolumeSlider() {
        volumeSlider.setOnMouseDragged(event -> {
            double value = volumeSlider.getValue();
            mediaPlayer.setVolume(value / 100);
        });

        volumeSlider.setOnMouseClicked(event -> {
            double value = volumeSlider.getValue();
            mediaPlayer.setVolume(value / 100);
        });
    }
}

