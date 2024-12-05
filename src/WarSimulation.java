import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

class WarSimulation {

    private Player player;
    private Computer computer;

    Timeline gameLoop;

    public WarSimulation() {
        Nation playerNation = new Nation("America");
        Nation computerNation = new Nation("Russia");

        this.player = new Player(playerNation);
        this.computer = new Computer(computerNation, player);
    }

    void startGame(Stage stage) {
        Pane p = new Pane();
        Scene s = new Scene(p, 1000, 500);
        stage.setScene(s);
        stage.setFullScreen(true);

        // Create a Timeline for the game loop
        gameLoop = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
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