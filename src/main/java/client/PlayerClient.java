package client;

import javafx.scene.media.Media;
import javafx.stage.FileChooser;

import java.io.File;

public class PlayerClient {

    // выбор медиафайла
    public static File chooseFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter("Media Files", "*.mp3", "*.mp4");
        fileChooser.getExtensionFilters().addAll(fileExtensions);
        File mediaFile = null;
        mediaFile = fileChooser.showOpenDialog(null);
        return mediaFile;
    }

    // время медиафайла в секундах
    public static double getDurationOfTheFileS(Media media) {
        return media.getDuration().toSeconds();
    }
}
