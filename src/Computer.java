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

        //Build nukes on the first turn
        if (firstTurn) {
            actions.add(new BuildNukeAction(nation));
            actions.add(new BuildNukeAction(nation));
            firstTurn = false;
            actionCount = 2;
        }

        // Loop to pick two actions per turn
        while (actionCount < MAX_ACTIONS) {
            actionCount++;
            //No resource actions
            if (nation.getResources() <= 0) {
                if (nation.getNumNukes() > 0 && chance < 9) {
                    actions.add(new LaunchNukeAction(nation, player.getNation()));
                } else if (nation.getNumSoldiers() > 0 && chance < 4) {
                    actions.add(new DeploySoldiersAction(nation, player.getNation(), 10));
                } else {
                    actions.add(new CollectResourcesAction(nation, 20));
                }
                continue;
            }

            //Low health high resource actions
            if (nation.getHealth() <= 50) {
                if (nation.getResources() >= 75 && chance < 5) {
                    actions.add(new StrengthenShieldAction(nation, 15));
                } else if (nation.getResources() >= 50 && chance < 5) {
                    actions.add(new BuildNukeAction(nation));
                } else if (nation.getResources() >= 10 && chance < 5) {
                    actions.add(new RecruitSoldiersAction(nation, 10));
                }

                //Low health violence
                if (nation.getNumNukes() > 0 && chance > 4) {
                    actions.add(new LaunchNukeAction(nation, player.getNation()));
                } else if (nation.getNumSoldiers() > 0 && chance > 4) {
                    actions.add(new DeploySoldiersAction(nation, player.getNation(), 10));
                } else {
                    actions.add(new CollectResourcesAction(nation, 20));
                }
                continue;
            }

            //High resource actions
            if (nation.getResources() >= 50) {
                if (chance < 8) {
                    actions.add(new BuildNukeAction(nation));
                } else {
                    actions.add(new RecruitSoldiersAction(nation, 10));
                }
                continue;
            }

            //Unload nukes and soldiers
            if (nation.getNumNukes() > 0) {
                actions.add(new LaunchNukeAction(nation, player.getNation()));
            } else if (nation.getNumSoldiers() > 0) {
                actions.add(new DeploySoldiersAction(nation, player.getNation(), 10));
            } else {
                actions.add(new CollectResourcesAction(nation, 20));
            }
        }
        resetTurnCount();
        return actions;
    }
}