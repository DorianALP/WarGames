import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Computer implements iPlay {

    private Nation nation;
    private Player player;

    private boolean firstTurn = true;
    private static final int MAX_ACTIONS = 2; // Two actions per turn
    private int actionCount = 0;
    Random r = new Random();

    public Computer(Nation nation, Player player) {
        this.nation = nation;
        this.player = player;
    }

    @Override
    public List<GameAction> chooseAction() {
        List<GameAction> actions = think();
        return actions;
    }

    @Override
    public Nation getNation() {
        return this.nation;
    }

    public void resetTurnCount() {
        this.actionCount = 0;
    }

    private List<GameAction> think() {
        List<GameAction> actions = new ArrayList<>();

        // Simulated state sariables so the computer can figure out best move. 
        // Keeping track of AI resources without affecting the game.
        int simulatedResources = nation.getResources();
        int simulatedNukes = nation.getNumNukes();
        int simulatedSoldiers = nation.getNumSoldiers();
        int simulatedShieldStrength = nation.getShieldStrength();
        int simulatedHealth = nation.getHealth();

        // First Turn Logic 
        if (firstTurn) {
            // Action 1: Attempt to build a nuke if resources allow
            if (simulatedResources >= 50 && actions.size() < MAX_ACTIONS) {
                actions.add(new BuildNukeAction(nation));
                simulatedResources -= 50;
                simulatedNukes += 1;
            } else if (actions.size() < MAX_ACTIONS) {
                // If not enough resources, collect resources instead
                actions.add(new CollectResourcesAction(nation, 20));
                simulatedResources += 20;
            }

            // Action 2: Attempt to build another nuke or collect resources
            if (actions.size() < MAX_ACTIONS) {
                if (simulatedResources >= 50) {
                    actions.add(new BuildNukeAction(nation));
                    simulatedResources -= 50;
                    simulatedNukes += 1;
                } else {
                    actions.add(new CollectResourcesAction(nation, 20));
                    simulatedResources += 20;
                }
            }

            firstTurn = false; // End first turn logic
            return actions; // Return actions for the first turn
        }

        // Subsequent Turns Logic 
        while (actions.size() < MAX_ACTIONS) {
            int chance = r.nextInt(10); // Generate a new chance for each action decision

            // Case 1: No Resources
            if (simulatedResources <= 0) {
                if (simulatedNukes > 0 && chance < 9) { // 90% chance to launch a nuke
                    actions.add(new LaunchNukeAction(nation, player.getNation()));
                    simulatedNukes -= 1;
                } else if (simulatedSoldiers >= 10 && chance < 4) { // 40% chance to deploy soldiers
                    actions.add(new DeploySoldiersAction(nation, player.getNation(), 10));
                    simulatedSoldiers -= 10;
                } else { // Default action: Collect resources
                    actions.add(new CollectResourcesAction(nation, 20));
                    simulatedResources += 20;
                }
                continue; // Proceed to the next action slot
            }

            // Case 2: Low Health and Sufficient Resources
            if (simulatedHealth <= 50) {
                // Action 1: Strengthen shield if possible
                if (simulatedResources >= 75 && chance < 5 && actions.size() < MAX_ACTIONS) { // 50% chance
                    actions.add(new StrengthenShieldAction(nation, 15));
                    simulatedResources -= 15;
                    simulatedShieldStrength += 15;
                }

                // Action 2: Build a nuke if possible
                if (simulatedResources >= 50 && chance < 5 && actions.size() < MAX_ACTIONS) { // 50% chance
                    actions.add(new BuildNukeAction(nation));
                    simulatedResources -= 50;
                    simulatedNukes += 1;
                }

                // Action 3: Recruit soldiers if possible
                if (simulatedResources >= 10 && chance < 5 && actions.size() < MAX_ACTIONS) { // 50% chance
                    actions.add(new RecruitSoldiersAction(nation, 10));
                    simulatedResources -= 10;
                    simulatedSoldiers += 10;
                }

                // Low health aggression: Launch nukes or deploy soldiers
                if (simulatedNukes > 0 && chance > 4 && actions.size() < MAX_ACTIONS) { // 50% chance
                    actions.add(new LaunchNukeAction(nation, player.getNation()));
                    simulatedNukes -= 1;
                } else if (simulatedSoldiers >= 10 && chance > 4 && actions.size() < MAX_ACTIONS) { // 50% chance
                    actions.add(new DeploySoldiersAction(nation, player.getNation(), 10));
                    simulatedSoldiers -= 10;
                } else if (actions.size() < MAX_ACTIONS) { // Default action
                    actions.add(new CollectResourcesAction(nation, 20));
                    simulatedResources += 20;
                }
                continue; // Proceed to the next action slot
            }

            // Case 3: High Resources
            if (simulatedResources >= 50) {
                if (chance < 8 && actions.size() < MAX_ACTIONS) { // 80% chance to build a nuke
                    actions.add(new BuildNukeAction(nation));
                    simulatedResources -= 50;
                    simulatedNukes += 1;
                } else if (simulatedResources >= 10 && actions.size() < MAX_ACTIONS) { // 20% chance to recruit soldiers
                    actions.add(new RecruitSoldiersAction(nation, 10));
                    simulatedResources -= 10;
                    simulatedSoldiers += 10;
                }
                continue; // Proceed to the next action slot
            }

            // Case 4: Unload Nukes and Soldiers
            if (simulatedNukes > 0 && actions.size() < MAX_ACTIONS) {
                actions.add(new LaunchNukeAction(nation, player.getNation()));
                simulatedNukes -= 1;
                continue;
            } else if (simulatedSoldiers >= 10 && actions.size() < MAX_ACTIONS) {
                actions.add(new DeploySoldiersAction(nation, player.getNation(), 10));
                simulatedSoldiers -= 10;
                continue;
            } else { // Default action: Collect resources
                actions.add(new CollectResourcesAction(nation, 20));
                simulatedResources += 20;
            }
        }

        return actions;
    }
}