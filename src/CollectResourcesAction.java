public class CollectResourcesAction extends GameAction {
    private int amountToCollect;

    public CollectResourcesAction(Nation nation, int amountToCollect) {
        super(nation);
        this.amountToCollect = amountToCollect;
    }

    @Override
    public String execute(Nation targetNation) {
        return nation.collectResources(amountToCollect);
    }
}
