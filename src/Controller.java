import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Controller {
    private Button collectResourcesButton;
    private Button recruitSoldiersButton;
    private Button buildNukeButton;
    private Button launchNukeButton;
    private TextArea gameLog;
    private Label playerResourcesLabel;
    private Label playerSoldiersLabel;
    private Label computerResourcesLabel;
    private Label computerSoldiersLabel;

    private Player player;
    private Computer computer;
    private WarSimulation warSimulation;

    private VBox mainLayout;

    public Controller(Stage stage) {
        // Initialize Nations
        Nation playerNation = new Nation("America");
        Nation computerNation = new Nation("Russia");
        player = new Player(playerNation);
        computer = new Computer(computerNation, player);
        warSimulation = new WarSimulation(player, computer, this);

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

        // Set up button actions
        collectResourcesButton.setOnAction(e -> handleAction(() -> {
            GameAction collectAction = new CollectResourcesAction(player.getNation(), 20);
            player.addAction(collectAction);
            logAction("Player chose to collect 20 resources.");
        }));

        recruitSoldiersButton.setOnAction(e -> handleAction(() -> {
            GameAction recruitAction = new RecruitSoldiersAction(player.getNation(), 10);
            player.addAction(recruitAction);
            logAction("Player chose to recruit 10 soldiers.");
        }));

        buildNukeButton.setOnAction(e -> handleAction(() -> {
            GameAction buildAction = new BuildNukeAction(player.getNation());
            player.addAction(buildAction);
            logAction("Player chose to build a nuke.");
        }));

        launchNukeButton.setOnAction(e -> handleAction(() -> {
            if (player.getNation().getNumNukes() > 0) {
                GameAction launchAction = new LaunchNukeAction(player.getNation(), computer.getNation());
                player.addAction(launchAction);
                logAction("Player chose to launch a nuke at Computer.");
            } else {
                logAction("Player cannot launch a nuke (no nukes available).");
            }
        }));

        // Layout setup
        mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));

        // Player Info
        VBox playerInfo = new VBox(5);
        playerInfo.getChildren().addAll(
            new Label("Player Nation: America"),
            playerResourcesLabel,
            playerSoldiersLabel
        );

        // Computer Info
        VBox computerInfo = new VBox(5);
        computerInfo.getChildren().addAll(
            new Label("Computer Nation: Russia"),
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

        // Set up the scene
        Scene gameScene = new Scene(mainLayout, 1000, 500);
        stage.setScene(gameScene);
        stage.show();
        stage.requestFocus();

        // Start the game loop
        warSimulation.startGame(stage);
    }

     // Handles adding an action and executing turns if MAX_ACTIONS is reached.
    private void handleAction(Runnable action) {
        if (player.getActionCount() < player.MAX_ACTIONS) {
            action.run();

            if (player.getActionCount() == player.MAX_ACTIONS) {
                // Disable buttons until Computer's turn is complete
                disableActionButtons();
                // Player's actions will be processed by WarSimulation's game loop
            }
        } else {
            logAction("Player has already selected maximum actions for this turn.");
        }
    }

    // Logs messages to the game log UI component.
    public void logAction(String message) {
        gameLog.appendText(message + "\n"); // UI log
    }

    // Updates the UI labels to reflect the current game state.
    public void updateLabels() {
        playerResourcesLabel.setText("Player Resources: " + player.getNation().getResources());
        playerSoldiersLabel.setText("Player Soldiers: " + player.getNation().getNumSoldiers());
        computerResourcesLabel.setText("Computer Resources: " + computer.getNation().getResources());
        computerSoldiersLabel.setText("Computer Soldiers: " + computer.getNation().getNumSoldiers());
    }


    // Disables all action buttons to prevent further actions until the Computer's turn is complete.
    private void disableActionButtons() {
        collectResourcesButton.setDisable(true);
        recruitSoldiersButton.setDisable(true);
        buildNukeButton.setDisable(true);
        launchNukeButton.setDisable(true);
    }

    // Enables all action buttons for the Player's turn.
    public void enableActionButtons() {
        collectResourcesButton.setDisable(false);
        recruitSoldiersButton.setDisable(false);
        buildNukeButton.setDisable(false);
        launchNukeButton.setDisable(false);
    }
}
