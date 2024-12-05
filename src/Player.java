import java.util.ArrayList;
import java.util.List;

public class Player implements iPlay {
    private Nation nation;
    private static final int MAX_ACTIONS = 2;
    private int actionCount = 0;
    private List<GameAction> actions = new ArrayList<>();

    public Player(Nation nation) {
        this.nation = nation;
    }

    @Override
    public List<GameAction> chooseAction() {
        return new ArrayList<>(actions);
    }

    @Override
    public Nation getNation() {
        return this.nation;
    }

    public void addAction(GameAction action) {
        if (actionCount < MAX_ACTIONS) {
            actions.add(action);
            actionCount++;
        } else {
            System.out.println("Maximum actions reached for this turn.");
        }
    }

    public void resetTurnCount() {
        this.actionCount = 0;
        this.actions.clear();
    }
}
