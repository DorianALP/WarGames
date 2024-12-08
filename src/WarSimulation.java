import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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
            if(isPlayersTurn) {
                // Player's Turn
                List<GameAction> playerActions = player.chooseAction();
                for (GameAction action : playerActions) {
                    action.execute(computer.getNation());
                    if (computer.getNation().isDefeated()) {
                        System.out.println("Player wins!");
                        stopGame(gameLoop);
                        break;
                    }
                }

                if (computer.getNation().isDefeated()) {
                    System.out.println("Player wins!");
                    stopGame(gameLoop);
                    return;
                }

                player.resetTurnCount();

                updateUI();

                isPlayersTurn = false;

            } else {

                // Computer's Turn
                List<GameAction> computerActions = computer.chooseAction();
                for (GameAction action : computerActions) {
                    action.execute(player.getNation());
                    if (player.getNation().isDefeated()) {
                        System.out.println("Computer wins!");
                        stopGame(gameLoop);
                        break;
                    }
                }

                if (player.getNation().isDefeated()) {
                    System.out.println("Computer wins!");
                    stopGame(gameLoop);
                    return;
                }

                // Reset action counts for the next turn
                player.resetTurnCount();
                computer.resetTurnCount();

                // Update UI here to reflect changes
                updateUI();

                isPlayersTurn = true;
            }
        }));

        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    private void updateUI() {
        // Implement UI update logic to reflect the current state of the game
        // e.g., update labels, progress bars, etc.
    }

    private void stopGame(Timeline gameLoop) {
        gameLoop.stop();
        // show a dialog or navigate to a game-over screen
    }
}