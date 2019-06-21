package controllers;

import client.PlayerClient;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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

    private File mediaFile = null;

    private Media media = null;

    private MediaPlayer mediaPlayer = null;

    // свойство изменение состояния плеера для перемещения слайдера
    private boolean change = false;

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
                // если трек уже играет и мы выбираем новый, то старый останавливается
//                if (mediaPlayer != null) {
//                    mediaPlayer.stop();
//                }
                mediaPlayer = new MediaPlayer(media);
                // указание длинны слайдера трека
                mediaPlayer.setOnReady(() -> setParametersToSliderOfTheTrack());
                // задание положения слайдеру громкости
                volumeSlider.setValue(mediaPlayer.getVolume() * 100);
                // изменение положения слайдера трека относительно текущего времени плеера
                mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                    @Override
                    public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                        Main.service.submit(() -> sliderOfTheTrack.setValue(mediaPlayer.getCurrentTime().toSeconds()));
                    }
                });

                mediaView.setMediaPlayer(mediaPlayer);
            }
        });
    }

    @FXML
    private void setEventToPauseStart() {
        play_pause.setOnMouseClicked(event -> startOrPauseMediaFile());
    }

    @FXML
    private void setActionsToSliderOfTheTrack() {
//        sliderOfTheTrack.setOnMouseDragged(event -> {
//            eventToSliderOfTheTrack();
//        });

        //измененить время проигрывания слайдером
        sliderOfTheTrack.setOnMouseClicked(event -> {
            if (MediaPlayer.Status.PLAYING.equals(mediaPlayer.getStatus())) {
                mediaPlayer.pause();
                // свойство изменение состояния плеера для перемещения слайдера
                change = true;
            }
            mediaPlayer.seek(Duration.seconds(sliderOfTheTrack.getValue()));
            // если плеер быд остановлен изначально, то запуск не выполняется
            if (MediaPlayer.Status.PAUSED.equals(mediaPlayer.getStatus()) && change) {
                // свойство изменение состояния плеера для перемещения слайдера
                change = !change;
                mediaPlayer.play();
            }
        });
    }

    @FXML
    private void setActionsToVolumeSlider() {
        // изменение громкости на плеере
        volumeSlider.setOnMouseDragged(event -> {
            double value = volumeSlider.getValue();
            mediaPlayer.setVolume(value / 100);
        });
        volumeSlider.setOnMouseClicked(event -> {
            double value = volumeSlider.getValue();
            mediaPlayer.setVolume(value / 100);
        });
    }

    private void setParametersToSliderOfTheTrack() {
        // указание максимального положения слайдера трека по его длинне
        sliderOfTheTrack.setMax(PlayerClient.getDurationOfTheFileS(media));
        // стартовое положение
        sliderOfTheTrack.setValue(0.0);
    }

    // пауза\запуск медиафайла
    private void startOrPauseMediaFile() {
        if (MediaPlayer.Status.PLAYING.equals(mediaPlayer.getStatus())) mediaPlayer.pause();
        else mediaPlayer.play();
        changeImagePlayPause();
    }

    // смена картинки паузы\запуска
    private void changeImagePlayPause() {
        if (MediaPlayer.Status.PLAYING.equals(mediaPlayer.getStatus())) {
            File file = new File("C:\\Users\\Andrey\\IDEA\\MusicPlayer\\src\\main\\resources\\icons\\-play.png");
            play_pause.setImage(new Image(file.toURI().toString()));
        } else {
            File file = new File("C:\\Users\\Andrey\\IDEA\\MusicPlayer\\src\\main\\resources\\icons\\pause.png");
            play_pause.setImage(new Image(file.toURI().toString()));
        }
    }

}

