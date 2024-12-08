abstract class GameAction {
    protected Nation nation;

    public GameAction(Nation nation) {
        this.nation = nation;
    }

    public abstract String execute(Nation target);
}