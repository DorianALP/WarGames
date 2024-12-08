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
    private Button collectResourcesButton, recruitSoldiersButton, buildNukeButton, launchNukeButton;
    private TextArea gameLog;

    private Label playerHealthLabel, playerResourcesLabel, playerSoldiersLabel;
    private Label computerHealthLabel, computerResourcesLabel, computerSoldiersLabel;

    private ProgressBar playerHealthBar, computerHealthBar;

    private Stage stage;
    private Player player;
    private Computer computer;
    private Controller controller;

    public View(Stage stage, Player player, Computer computer) {
        this.stage = stage;
        this.player = player;
        this.computer = computer;

        initializeUIComponents();
        createStartupScreen();
    }

    public void setController(Controller controller) {
        this.controller = controller;

        // Attach actions to buttons with Controller logic
        collectResourcesButton.setOnAction(e -> Controller.handleAction(() -> Controller.collectResources()));
        recruitSoldiersButton.setOnAction(e -> Controller.handleAction(() -> Controller.recruitSoldiers()));
        buildNukeButton.setOnAction(e -> Controller.handleAction(() -> Controller.buildNuke()));
        launchNukeButton.setOnAction(e -> Controller.handleAction(() -> Controller.launchNuke()));
    }

    // Initialize UI Components
    private void initializeUIComponents() {
        collectResourcesButton = new Button("Collect Resources");
        recruitSoldiersButton = new Button("Recruit Soldiers");
        buildNukeButton = new Button("Build Nuke");
        launchNukeButton = new Button("Launch Nuke");

        playerHealthBar = new ProgressBar(1);
        computerHealthBar = new ProgressBar(1);

        playerHealthLabel = new Label("Health: 100%");
        playerResourcesLabel = new Label("Resources: 100");
        playerSoldiersLabel = new Label("Soldiers: 0");

        computerHealthLabel = new Label("Health: 100%");
        computerResourcesLabel = new Label("Resources: 100");
        computerSoldiersLabel = new Label("Soldiers: 0");

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

        response.setOnKeyReleased(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                if (response.getText().trim().equalsIgnoreCase("Y")) {
                    showGameRules();
                } else if (response.getText().trim().equalsIgnoreCase("N")) {
                    stage.close();
                }
            }
        });

        root.getChildren().addAll(greeting, response);
        root.setStyle("-fx-alignment: center; -fx-padding: 50;");
        stage.setScene(new Scene(root));
        stage.show();
    }

    // --- GAME RULES SCREEN ---
    private void showGameRules() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));

        Label title = new Label("Game Rules");
        title.setFont(Font.font("Monospaced", 40));
        title.setTextFill(Color.DARKGREEN);

        TextArea rules = new TextArea(
            "Goal: Reduce the opponent's health to 0.\n" +
            "\nActions:\n" +
            "1. Collect Resources: +20 resources (Free)\n" +
            "2. Recruit Soldiers: Costs 100 resources\n" +
            "3. Build Nuke: Costs 50 resources\n" +
            "4. Launch Nuke: Deals 50 damage (Requires 1 nuke)\n\n" +
            "You can take 2 actions per turn. Good luck!"
        );
        rules.setWrapText(true);
        rules.setEditable(false);
        rules.setStyle("-fx-control-inner-background: white; -fx-text-fill: black;");
        rules.setPrefHeight(300);

        Button startGameButton = new Button("Start Game");
        startGameButton.setOnAction(e -> createGameScreen());

        root.getChildren().addAll(title, rules, startGameButton);
        stage.setScene(new Scene(root, 1000, 600));
    }

    // --- GAME SCREEN ---
    private void createGameScreen() {
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));

        VBox playerBox = createNationBox("Player Nation: America", playerHealthBar, playerHealthLabel, playerResourcesLabel, playerSoldiersLabel);
        VBox computerBox = createNationBox("Computer Nation: Russia", computerHealthBar, computerHealthLabel, computerResourcesLabel, computerSoldiersLabel);

        HBox buttonBox = new HBox(10, collectResourcesButton, recruitSoldiersButton, buildNukeButton, launchNukeButton);
        buttonBox.setAlignment(Pos.CENTER);

        root.setLeft(playerBox);
        root.setRight(computerBox);
        root.setCenter(gameLog);
        root.setBottom(buttonBox);

        stage.setScene(new Scene(root, 1000, 600));
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
        playerHealthBar.setProgress(player.getNation().getHealth() / 100.0);

        computerHealthLabel.setText("Health: " + computer.getNation().getHealth());
        computerResourcesLabel.setText("Resources: " + computer.getNation().getResources());
        computerSoldiersLabel.setText("Soldiers: " + computer.getNation().getNumSoldiers());
        computerHealthBar.setProgress(computer.getNation().getHealth() / 100.0);
    }

    public TextArea getGameLog() {
        return gameLog;
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

