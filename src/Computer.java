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
        int chance = r.nextInt(10);

        // Recruit soldiers on the first turn
        if (firstTurn && actionCount < MAX_ACTIONS && chance < 5) {
            actions.add(new RecruitSoldiersAction(nation, 5));
            firstTurn = false;
            actionCount++;
        }

        // Loop to pick two actions per turn
        while (actionCount < MAX_ACTIONS) {
            if (nation.getHealth() >= player.getNation().getHealth() && chance < 8 || chance > 7 && nation.getHealth() <= player.getNation().getHealth()) {
                if (nation.getNumNukes() > 0) {
                    actions.add(new LaunchNukeAction(nation, player.getNation()));
                } else if (nation.getResources() >= 50) { // Assuming 50 resources to build a nuke
                    actions.add(new BuildNukeAction(nation));
                } else {
                    actions.add(new CollectResourcesAction(nation, 10)); // Collect additional resources
                }
            } else {
                if (nation.getShieldStrength() <= 25) {
                    actions.add(new StrengthenShieldAction(nation, 15));
                } else {
                    actions.add(new DeploySoldiersAction(nation, player.getNation(), 5));
                }
            }
            actionCount++;
        }

        return actions;
    }
}
