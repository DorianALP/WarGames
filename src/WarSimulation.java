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
    private boolean isPlayersTurn = false; // Starts with Computer's turn after Player's actions

    public WarSimulation(Player player, Computer computer, Controller controller) {
        this.player = player;
        this.computer = computer;
        this.controller = controller;
    }

    void startGame(Stage stage) {
        // Create a Timeline for the game loop
        gameLoop = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // Update UI here to reflect changes
            updateUI();
            if(isPlayersTurn) {
                // Player's Turn
                controller.enableActionButtons();
                if(player.getActionCount() == 2) {
                    List<GameAction> playerActions = player.chooseAction();
                    for (GameAction action : playerActions) {
                        Controller.logAction(action.execute(computer.getNation()));;
                        if (computer.getNation().isDefeated()) {
                            Controller.logAction("Player wins!");
                            stopGame(gameLoop);
                            return;
                        }
                    }

                    // Reset action counts for the next turn
                    player.resetTurnCount();

                    isPlayersTurn = false;
                } 

            } else {

                // Computer's Turn
                List<GameAction> computerActions = computer.chooseAction();
                for (GameAction action : computerActions) {
                    Controller.logAction(action.execute(player.getNation()));
                    if (player.getNation().isDefeated()) {
                        Controller.logAction("Computer wins!");
                        stopGame(gameLoop);
                        return;
                    }
                }

                // Reset action counts for the next turn
                computer.resetTurnCount();

                isPlayersTurn = true;
            }
        }));

        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    private void updateUI() {
        // UI update logic to reflect the current state of the game
        Controller.updateLabels();
    }

    private void stopGame(Timeline gameLoop) {
        gameLoop.stop();
        // Maybe show a dialog or navigate to a game-over screen
    }
}