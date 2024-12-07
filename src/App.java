import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        VBox vb = new VBox();
        Label greeting = new Label("HELLO JOSHUA.\nWOULD YOU LIKE TO PLAY A GAME?\nY/N?");
        greeting.setTextFill(Color.GREEN);
        greeting.setFont(new Font(35));
        TextField response = new TextField();
        response.setBackground(Background.EMPTY);
        response.setFont(new Font(35));
        vb.getChildren().add(greeting);
        vb.getChildren().add(response);
        Scene scene = new Scene(vb, 1000, 500);
        vb.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
        scene.setOnKeyReleased(e -> {
            if(e.getCode().equals(KeyCode.ENTER)) {
                if(response.getText().equals("Y")) {
                    new Controller(stage);
                } else if (response.getText().equals("N")) {
                    stage.close();
                }
            }
        });
    }
    public static void main(String[] args) throws Exception {
        Application.launch(args);
    }
}