// View.java
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
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
    
    public View(Player player, Computer computer) {
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
