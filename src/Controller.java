import javafx.stage.Stage;

public class Controller {
    private static View view;
    private static Player player;
    private static Computer computer;
    private WarSimulation warSimulation;

    public Controller(Stage stage, View v, Player p, Computer c) 
    {
        view = v;
        player = p;
        computer = c;

        warSimulation = new WarSimulation(player, computer, this);

        // Start the game loop
        warSimulation.startGame(stage);
    }

    // Handles adding an action and executing turns if MAX_ACTIONS is reached.
    public static void handleAction(Runnable action) {
        if (player.getActionCount() < player.MAX_ACTIONS) {
            action.run();

            if (player.getActionCount() >= player.MAX_ACTIONS) {
                // Disable buttons until Computer's turn is complete
                disableActionButtons();
                // Player's actions will be processed by WarSimulation's game loop
            }
        } else {
            logAction("-----[SYSTEM] Player has already selected maximum actions for this turn.-----");
        }
    }

    // Logs messages to the game log UI component.
    public static void logAction(String message) {
        view.getGameLog().appendText(message + "\n"); // UI log
    }

    // Disables all action buttons to prevent further actions until the Computer's turn is complete.
    private static void disableActionButtons() {
        view.getCollectResourcesButton().setDisable(true);
        view.getRecruitSoldiersButton().setDisable(true);
        view.getBuildNukeButton().setDisable(true);
        view.getLaunchNukeButton().setDisable(true);
        view.getDeploySoldiersButtons().setDisable(true);
        view.getStrengthenShieldButton().setDisable(true);
    }

    // Enables all action buttons for the Player's turn.
    public void enableActionButtons() {
        view.getCollectResourcesButton().setDisable(false);
        view.getRecruitSoldiersButton().setDisable(false);
        view.getBuildNukeButton().setDisable(false);
        view.getLaunchNukeButton().setDisable(false);
        view.getDeploySoldiersButtons().setDisable(false);
        view.getStrengthenShieldButton().setDisable(false);

    }

    public static void collectResources(){
        GameAction collectAction = new CollectResourcesAction(player.getNation(), 20);
        player.addAction(collectAction);
        logAction("-----[SYSTEM] Player chose to collect 20 resources.-----");
    }

    public static void strengthenShield(){
        if(player.getNation().getResources() >= 15) {
            if(player.getNation().getShieldStrength() <= 85) {
                GameAction repairAction = new StrengthenShieldAction(player.getNation(), 15);
                player.addAction(repairAction);
                logAction("-----[SYSTEM] Player chose to repair their shield by 15.-----");
            } else if(player.getNation().getShieldStrength() > 85 && player.getNation().getShieldStrength() < 100) {
                GameAction repairAction = new StrengthenShieldAction(player.getNation(), 100 - player.getNation().getShieldStrength());
                player.addAction(repairAction);
                logAction("-----[SYSTEM] Player chose to repair their shield.-----");
            } else {
                logAction("-----[SYSTEM] Player shield is already full.-----");

            }
        } else {
            logAction("-----[SYSTEM] Player doesn't have the resources to strengthen shield.-----");
        }
    }

    public static void recruitSoldiers(){
        if(player.getNation().getResources() >= 10) {
            GameAction recruitAction = new RecruitSoldiersAction(player.getNation(), 10);
            player.addAction(recruitAction);
            logAction("-----[SYSTEM] Player chose to recruit 10 soldiers.-----");
        } else {
            logAction("-----[SYSTEM] Player doesn't have enough resources to recruit soldiers..-----");

        }
    }

    public static void deploySoldiers() {
        int soldiersToDeploy = 10; // Fixed soldiers deployed
        if (player.getNation().getNumSoldiers() >= soldiersToDeploy) {
            GameAction deployAction = new DeploySoldiersAction(player.getNation(), computer.getNation(), soldiersToDeploy);
            player.addAction(deployAction);
            logAction("-----[SYSTEM] Soldiers deployed!-----");

        } else {
            logAction("-----[SYSTEM] Not enough soldiers to deploy!-----");
        }
    }

    public static void buildNuke(){
        if(player.getNation().getResources() >= 50) {
            GameAction buildAction = new BuildNukeAction(player.getNation());
            player.addAction(buildAction);
            logAction("-----[SYSTEM] Player chose to build a nuke.-----");
        } else {
            logAction("-----[SYSTEM] Player doesn't have the resources to build a nuke.-----");

        }
    }

    public static void launchNuke() {
        if (player.getNation().getNumNukes() > 0) {
            GameAction launchAction = new LaunchNukeAction(player.getNation(), computer.getNation());
            player.addAction(launchAction);
            logAction("-----[SYSTEM] Player launched a nuke.-----");
        } else {
            logAction("-----[SYSTEM] Player cannot launch a nuke (no nukes available).-----");
        }
    }

    // Getter for View
    public View getView() {
        return view;
    }
}