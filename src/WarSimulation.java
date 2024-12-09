import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WarSimulation {
    private Player player;
    private Computer computer;
    private Timeline gameLoop;
    private Controller controller;
    private boolean isPlayersTurn = true; // Start with Player's turn

    public WarSimulation(Player player, Computer computer, Controller controller) {
        this.player = player;
        this.computer = computer;
        this.controller = controller;
    }

    void startGame(Stage stage) {
        // Create a Timeline for the game loop
        gameLoop = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            controller.getView().updateUI(); // Update UI every tick

            if (isPlayersTurn) {
                // Enable player actions
                controller.enableActionButtons();

                if (player.getActionCount() == 2) { // Player finished turn
                    processPlayerTurn();
                    isPlayersTurn = false;
                }
            } else {
                // Computer's Turn
                processComputerTurn();
                isPlayersTurn = true;
            }
        }));

        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    private void processPlayerTurn() {
        List<GameAction> playerActions = player.chooseAction();
        for (GameAction action : playerActions) {
            Controller.logAction(action.execute(computer.getNation()));
            if (computer.getNation().isDefeated()) {
                Controller.logAction("Player wins!");
                stopGame();
                return;
            }
        }
        player.resetTurnCount();
    }

    private void processComputerTurn() {
        List<GameAction> computerActions = computer.chooseAction();
        for (GameAction action : computerActions) {
            Controller.logAction(action.execute(player.getNation()));
            if (player.getNation().isDefeated()) {
                Controller.logAction("Computer wins!");
                stopGame();
                return;
            }
        }
        computer.resetTurnCount();
    }

    private void stopGame() {
        gameLoop.stop();
        controller.logAction("Game Over!");
        controller.getView().showGameOverScreen(
            player.getNation().isDefeated() ? "Computer Wins!" : "Player Wins!"
        );
    }
}