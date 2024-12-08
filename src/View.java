import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;

public class View {
    private Button collectResourcesButton;
    private Button recruitSoldiersButton;
    private Button buildNukeButton;
    private Button launchNukeButton;
    private TextArea gameLog;
    private Label playerResourcesLabel;
    private Label playerSoldiersLabel;
    private Label computerResourcesLabel;
    private Label computerSoldiersLabel;
    private VBox mainLayout;

    public View(Stage stage) {
        // Layout setup
        VBox root = new VBox(20);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        root.setPrefSize(1000, 500);

        // Setup the scene
        Scene scene = new Scene(root);
        stage.setScene(scene);

        // Greeting Label
        Label greeting = new Label("HELLO JOSHUA.\nWOULD YOU LIKE TO PLAY A GAME?\nY/N?");
        greeting.setTextFill(Color.LIMEGREEN);
        greeting.setFont(Font.font("Monospaced", 35));
        greeting.setEffect(new DropShadow(10, Color.LIMEGREEN));

        // TextField for user input
        TextField response = new TextField();
        response.setBackground(Background.EMPTY);
        response.setStyle("-fx-text-fill: limegreen; -fx-font-size: 35px; -fx-font-family: 'Monospaced';");
        response.setFocusTraversable(false);

        // Blinking cursor effect
        Timeline cursorBlink = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
            if (response.getPromptText().isEmpty()) {
                response.setPromptText("Type Y or N and press ENTER...");
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
            if (e.getCode().toString().equals("ENTER")) { // Trigger only on ENTER key
                String input = response.getText().trim().toUpperCase();
                switch (input) { // Convert input to uppercase
                    case "Y":
                        createGame();
                        new Controller(stage); // Start the game
                        cursorBlink.stop();
                        break;
                    case "N":
                        stage.close(); // Close the game
                        break;
                }
            }
        });

        root.getChildren().addAll(greeting, response);
        root.setSpacing(30);
        root.setStyle("-fx-alignment: center; -fx-padding: 50;");

    }
    
    public void createGame() {
        Nation playerNation = new Nation("America");
        Nation computerNation = new Nation("Russia");
        Player player = new Player(playerNation);
        Computer computer = new Computer(computerNation, player);

        // Initialize UI components
        collectResourcesButton = new Button("Collect Resources");
        recruitSoldiersButton = new Button("Recruit Soldiers");
        buildNukeButton = new Button("Build Nuke");
        launchNukeButton = new Button("Launch Nuke");
    
        gameLog = new TextArea();
        gameLog.setEditable(false);
        gameLog.setPrefHeight(200);
    
        playerResourcesLabel = new Label("Player Resources: " + player.getNation().getResources());
        playerSoldiersLabel = new Label("Player Soldiers: " + player.getNation().getNumSoldiers());
        computerResourcesLabel = new Label("Computer Resources: " + computer.getNation().getResources());
        computerSoldiersLabel = new Label("Computer Soldiers: " + computer.getNation().getNumSoldiers());
    
        // Layout setup
        mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));
    
        // Player Info
        VBox playerInfo = new VBox(5);
        playerInfo.getChildren().addAll(
            new Label("Player Nation: " + player.getNation().getName()),
            playerResourcesLabel,
            playerSoldiersLabel
        );
    
        // Computer Info
        VBox computerInfo = new VBox(5);
        computerInfo.getChildren().addAll(
            new Label("Computer Nation: " + computer.getNation().getName()),
            computerResourcesLabel,
            computerSoldiersLabel
        );
    
        // Action Buttons
        HBox actionButtons = new HBox(10);
        actionButtons.getChildren().addAll(
            collectResourcesButton,
            recruitSoldiersButton,
            buildNukeButton,
            launchNukeButton
        );
    
        // Add all components to main layout
        mainLayout.getChildren().addAll(
            playerInfo,
            actionButtons,
            computerInfo,
            new Label("Game Log:"),
            gameLog
        );

        collectResourcesButton.setOnAction(e -> Controller.handleAction(() -> {
            Controller.collectResources();
        }));

        recruitSoldiersButton.setOnAction(e -> Controller.handleAction(() -> {
            Controller.recruitSoldiers();
        }));

        buildNukeButton.setOnAction(e -> Controller.handleAction(() -> {
            Controller.buildNuke();
        }));

        launchNukeButton.setOnAction(e -> Controller.handleAction(() -> {
            Controller.launchNuke();
        }));
    }
    
    // Getters for UI components
    public VBox getMainLayout() {
        return mainLayout;
    }
    
    public Button getCollectResourcesButton() {
        return collectResourcesButton;
    }
    
    public Button getRecruitSoldiersButton() {
        return recruitSoldiersButton;
    }
    
    public Button getBuildNukeButton() {
        return buildNukeButton;
    }
    
    public Button getLaunchNukeButton() {
        return launchNukeButton;
    }
    
    public TextArea getGameLog() {
        return gameLog;
    }
    
    public Label getPlayerResourcesLabel() {
        return playerResourcesLabel;
    }
    
    public Label getPlayerSoldiersLabel() {
        return playerSoldiersLabel;
    }
    
    public Label getComputerResourcesLabel() {
        return computerResourcesLabel;
    }
    
    public Label getComputerSoldiersLabel() {
        return computerSoldiersLabel;
    }
}
