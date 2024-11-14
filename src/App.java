import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        Pane p = new Pane();
        Scene scene = new Scene(p, 1000, 500);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }
}