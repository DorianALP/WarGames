import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Controller {
    private static View view;
    private static Player player;
    private static Computer computer;
    private WarSimulation warSimulation;

    private VBox mainLayout;

    public Controller(Stage stage) {
        warSimulation = new WarSimulation(player, computer, this);

        // Set up the scene
        Scene gameScene = new Scene(mainLayout, 1000, 500);
        stage.setScene(gameScene);
        stage.show();

        // Start the game loop
        warSimulation.startGame(stage);
    }

     // Handles adding an action and executing turns if MAX_ACTIONS is reached.
    public static void handleAction(Runnable action) {
        if (player.getActionCount() < player.MAX_ACTIONS) {
            action.run();
            updateLabels();

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
    public static void logAction(String message) {
        System.out.println(message); // Console log
        view.getGameLog().appendText(message + "\n"); // UI log
    }

    // Updates the UI labels to reflect the current game state.
    public static void updateLabels() {
        view.getPlayerResourcesLabel().setText("Player Resources: " + player.getNation().getResources());
        view.getPlayerSoldiersLabel().setText("Player Soldiers: " + player.getNation().getNumSoldiers());
        view.getComputerResourcesLabel().setText("Computer Resources: " + computer.getNation().getResources());
        view.getComputerSoldiersLabel().setText("Computer Soldiers: " + computer.getNation().getNumSoldiers());
    }


    // Disables all action buttons to prevent further actions until the Computer's turn is complete.
    private static void disableActionButtons() {
        view.getCollectResourcesButton().setDisable(true);
        view.getRecruitSoldiersButton().setDisable(true);
        view.getBuildNukeButton().setDisable(true);
        view.getLaunchNukeButton().setDisable(true);
    }

    // Enables all action buttons for the Player's turn.
    public void enableActionButtons() {
        view.getCollectResourcesButton().setDisable(false);
        view.getRecruitSoldiersButton().setDisable(false);
        view.getBuildNukeButton().setDisable(false);
        view.getLaunchNukeButton().setDisable(false);
    }

    public static void collectResources(){
        GameAction collectAction = new CollectResourcesAction(player.getNation(), 20);
        player.addAction(collectAction);
        logAction("Player chose to collect 20 resources.");
    }

    public static void recruitSoldiers(){
        GameAction recruitAction = new RecruitSoldiersAction(player.getNation(), 10);
        player.addAction(recruitAction);
        logAction("Player chose to recruit 10 soldiers.");
    }

    public static void buildNuke(){
        GameAction buildAction = new BuildNukeAction(player.getNation());
        player.addAction(buildAction);
        logAction("Player chose to build a nuke.");
    }

    public static void launchNuke(){
        if (player.getNation().getNumNukes() > 0) {
            GameAction launchAction = new LaunchNukeAction(player.getNation(), computer.getNation());
            player.addAction(launchAction);
            logAction("Player chose to launch a nuke at Computer.");
        } else {
            logAction("Player cannot launch a nuke (no nukes available).");
        }
    }
}