import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

class WarSimulation {
    void startGame(Stage stage) {
        Pane p = new Pane();
        Scene s = new Scene(p, 1000, 500);
        stage.setScene(s);
        stage.setFullScreen(true);
    }
}