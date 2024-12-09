import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class View {
    private Button collectResourcesButton, recruitSoldiersButton, buildNukeButton, launchNukeButton, deploySoldiersButton, strengthenShieldButton;
    private TextArea gameLog;

    private Label playerHealthLabel, playerResourcesLabel, playerSoldiersLabel, playerShieldLabel;
    private Label computerHealthLabel, computerResourcesLabel, computerSoldiersLabel, computerShieldLabel;

    private ProgressBar playerHealthBar, computerHealthBar;

    private Stage stage;
    private Player player;
    private Computer computer;

    public View(Stage stage, Player player, Computer computer) {
        this.stage = stage;
        this.player = player;
        this.computer = computer;

        initializeUIComponents();
        createStartupScreen();
    }

    public void setController() {
        // Attach actions to buttons with Controller logic
        collectResourcesButton.setOnAction(_ -> Controller.handleAction(() -> Controller.collectResources()));
        recruitSoldiersButton.setOnAction(_ -> Controller.handleAction(() -> Controller.recruitSoldiers()));
        buildNukeButton.setOnAction(_ -> Controller.handleAction(() -> Controller.buildNuke()));
        launchNukeButton.setOnAction(_ -> Controller.handleAction(() -> Controller.launchNuke()));
        deploySoldiersButton.setOnAction(_ -> Controller.handleAction(() -> Controller.deploySoldiers()));
        strengthenShieldButton.setOnAction(_ -> Controller.handleAction(() -> Controller.strengthenShield()));
    }

    // Initialize UI Components
    private void initializeUIComponents() {
        collectResourcesButton = new Button("Collect Resources");
        recruitSoldiersButton = new Button("Recruit Soldiers");
        buildNukeButton = new Button("Build Nuke");
        launchNukeButton = new Button("Launch Nuke");
        deploySoldiersButton = new Button("Deploy Soldiers");
        strengthenShieldButton = new Button("Strengthen Shield");

        playerHealthBar = new ProgressBar(1);
        computerHealthBar = new ProgressBar(1);

        playerHealthLabel = new Label("Health: 100%");
        playerResourcesLabel = new Label("Resources: 100");
        playerSoldiersLabel = new Label("Soldiers: 0");
        playerShieldLabel = new Label("Shield: 100");

        computerHealthLabel = new Label("Health: 100%");
        computerResourcesLabel = new Label("Resources: 100");
        computerSoldiersLabel = new Label("Soldiers: 0");
        computerShieldLabel = new Label("Shield: 100");


        gameLog = new TextArea();
        gameLog.setEditable(false);
    }

    // --- STARTUP SCREEN ---
    private void createStartupScreen() {
        VBox root = new VBox(20);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        root.setPrefSize(1000, 500);

        Label greeting = new Label("HELLO JOSHUA.\nWOULD YOU LIKE TO PLAY A GAME?\nY/N?");
        greeting.setTextFill(Color.LIMEGREEN);
        greeting.setFont(Font.font("Monospaced", 35));
        greeting.setEffect(new DropShadow(10, Color.LIMEGREEN));

        TextField response = new TextField();
        response.setStyle("-fx-text-fill: limegreen; -fx-font-size: 35px; -fx-font-family: 'Monospaced';");
        response.setFocusTraversable(false);

        Timeline cursorBlink = new Timeline(new KeyFrame(Duration.seconds(0.5), _ -> {
            if (response.getPromptText().isEmpty()) response.setPromptText("Type Y or N and press ENTER...");
            else response.setPromptText("");
        }));
        cursorBlink.setCycleCount(Animation.INDEFINITE);
        cursorBlink.play();

        response.setOnKeyReleased(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                if (response.getText().trim().equalsIgnoreCase("Y")) {
                    fadeToGameRules();
                    cursorBlink.stop();
                } else if (response.getText().trim().equalsIgnoreCase("N")) stage.close();
            }
        });

        root.getChildren().addAll(greeting, response);
        root.setStyle("-fx-alignment: center; -fx-padding: 50;");
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    // --- FADE TRANSITION TO GAME RULES ---
    private void fadeToGameRules() {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), stage.getScene().getRoot());
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(_ -> showGameRules());
        fadeOut.play();
    }

    // --- GAME RULES SCREEN ---
    private void showGameRules() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));

        Label rulesTitle = new Label("Game Rules");
        rulesTitle.setFont(Font.font("Monospaced", 40));
        rulesTitle.setTextFill(Color.DARKGREEN);

        TextArea rulesText = new TextArea(
                "Welcome to WarGames!\n\n" +
                "Your goal is to defeat the opposing nation by reducing their health to 0.\n\n" +
                "Actions and Costs:\n" +
                "- Collect Resources: +20 resources (Free)\n" +
                "- Recruit Soldiers: 10 soldiers for 100 resources\n" +
                "- Build Nuke: 1 nuke for 50 resources\n" +
                "- Launch Nuke: Deals 50 damage (Requires 1 nuke)\n\n" +
                "Each turn, you can take up to 2 actions. The game alternates between you and the computer.\n" +
                "Make wise choices and watch your health, soldiers, and resources!"
        );
        rulesText.setWrapText(true);
        rulesText.setEditable(false);
        rulesText.setPrefHeight(300);
        rulesText.setStyle("-fx-control-inner-background: #F5F5F5; -fx-text-fill: black;");

        Button startGameButton = new Button("Fight!");
        startGameButton.setFont(Font.font(20));
        startGameButton.setOnAction(_ -> createGameScreen());

        root.getChildren().addAll(rulesTitle, rulesText, startGameButton);
        stage.setScene(new Scene(root, 1000, 600));
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
    }

    // --- GAME SCREEN ---
    private void createGameScreen() {
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));

        VBox playerBox = createNationBox("Player Nation: America", playerHealthBar, playerHealthLabel, playerResourcesLabel, playerSoldiersLabel, playerShieldLabel);
        VBox computerBox = createNationBox("Computer Nation: Russia", computerHealthBar, computerHealthLabel, computerResourcesLabel, computerSoldiersLabel, computerShieldLabel);

        HBox buttonBox = new HBox(10, collectResourcesButton, recruitSoldiersButton, deploySoldiersButton, buildNukeButton, launchNukeButton, strengthenShieldButton);
        buttonBox.setAlignment(Pos.CENTER);

        root.setLeft(playerBox);
        root.setRight(computerBox);
        root.setCenter(gameLog);
        root.setBottom(buttonBox);

        stage.setScene(new Scene(root, 1000, 600));
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }

    private VBox createNationBox(String title, ProgressBar healthBar, Label... stats) {
        VBox box = new VBox(10);
        box.setAlignment(Pos.TOP_CENTER);
        Label nationLabel = new Label(title);
        nationLabel.setFont(Font.font(25));
        nationLabel.setTextFill(Color.DARKGREEN);
        box.getChildren().addAll(nationLabel, healthBar);
        box.getChildren().addAll(stats);
        return box;
    }

    public void updateUI() {
        playerHealthLabel.setText("Health: " + player.getNation().getHealth());
        playerResourcesLabel.setText("Resources: " + player.getNation().getResources());
        playerSoldiersLabel.setText("Soldiers: " + player.getNation().getNumSoldiers());
        playerShieldLabel.setText("Shield: " + player.getNation().getShieldStrength());
        playerHealthBar.setProgress(player.getNation().getHealth() / 100.0);
    
        computerHealthLabel.setText("Health: " + computer.getNation().getHealth());
        computerResourcesLabel.setText("Resources: " + computer.getNation().getResources());
        computerSoldiersLabel.setText("Soldiers: " + computer.getNation().getNumSoldiers());
        computerShieldLabel.setText("Shield: " + computer.getNation().getShieldStrength());
        computerHealthBar.setProgress(computer.getNation().getHealth() / 100.0);
    }

    public void showGameOverScreen(String winnerMessage) {
        VBox gameOverLayout = new VBox(20);
        gameOverLayout.setAlignment(Pos.CENTER);
        gameOverLayout.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
    
        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.setFont(Font.font("Monospaced", 50));
        gameOverLabel.setTextFill(Color.DARKRED);
    
        Label winnerLabel = new Label(winnerMessage);
        winnerLabel.setFont(Font.font("Monospaced", 30));
        winnerLabel.setTextFill(Color.DARKGREEN);
    
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(_ -> stage.close());
    
        gameOverLayout.getChildren().addAll(gameOverLabel, winnerLabel, exitButton);
    
        Scene gameOverScene = new Scene(gameOverLayout, 1000, 600);
        stage.setScene(gameOverScene);
        stage.show();
    }

    public TextArea getGameLog() {
        return gameLog;
    }

    public Button getCollectResourcesButton() {
        return collectResourcesButton;
    }

    public Button getStrengthenShieldButton() {
        return strengthenShieldButton;
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

    public Button getDeploySoldiersButtons(){
        return deploySoldiersButton;
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

    public Label getComputerShieldLabel() {
        return computerShieldLabel;
    }
    public Label getPlayerShieldLabel() {
        return playerShieldLabel;
    }

    
}

