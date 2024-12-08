import javafx.animation.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class View {

    public Scene createStartupScreen(Stage stage) {
        // Layout setup
        VBox root = new VBox(20);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        root.setPrefSize(1000, 500);

        // Greeting Label
        Label greeting = new Label("HELLO JOSHUA.\nWOULD YOU LIKE TO PLAY A GAME?\nY/N?");
        greeting.setTextFill(Color.LIMEGREEN);
        greeting.setFont(Font.font("Monospaced", 35));
        greeting.setEffect(new DropShadow(10, Color.LIMEGREEN));

        // TextField for user input
        TextField response = new TextField();
        response.setBackground(Background.EMPTY);
        response.setStyle("-fx-text-fill: limegreen; -fx-font-size: 35px; -fx-font-family: 'Monospaced';");
        response.setPromptText("Type Y or N and press ENTER...");
        response.setFocusTraversable(false);

        // Blinking cursor effect
        Timeline cursorBlink = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
            if (response.getPromptText().isEmpty()) {
                response.setPromptText("Type Y or N...");
            } else {
                response.setPromptText("");
            }
        }));
        cursorBlink.setCycleCount(Animation.INDEFINITE);
        cursorBlink.play();

        // Fade-in animation
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), greeting);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        // Event handling for ENTER key
        response.setOnKeyReleased(e -> {
            switch (response.getText().trim().toUpperCase()) {
                case "Y":
                    new Controller(stage); // Start the game
                    cursorBlink.stop();
                    break;
                case "N":
                    stage.close(); // Close the game
                    break;
            }
        });

        root.getChildren().addAll(greeting, response);
        root.setSpacing(30);
        root.setStyle("-fx-alignment: center; -fx-padding: 50;");

        return new Scene(root); //???
    }
}
