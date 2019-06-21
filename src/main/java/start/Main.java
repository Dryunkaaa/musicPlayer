package start;

import controllers.PlayerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {

    public static Stage window;
    public static ExecutorService service = Executors.newFixedThreadPool(8);

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        // создание сцены плеера
        PlayerController playerController = new PlayerController();
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("fxmls/player.fxml"));
        // явное указание контроллера FXML-документа
        loader.setController(playerController);
        //загрузка документа
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root, 734, 559);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Player");
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            service.shutdown();
            System.exit(0);
        });
    }
}
