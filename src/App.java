import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Nation playerNation = new Nation("America");
        Nation computerNation = new Nation("Russia");
        Player player = new Player(playerNation);
        Computer computer = new Computer(computerNation, player);
        View view = new View(player, computer, stage);

        // Stage setup
        stage.setTitle("WarGames Startup");
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}