import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        View view = new View();
        Scene startupScene = view.createStartupScreen(stage); // Call the method from View

        // Stage setup
        stage.setTitle("WarGames Startup");
        stage.setScene(startupScene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}