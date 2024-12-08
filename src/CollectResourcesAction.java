public class CollectResourcesAction extends GameAction {
    private int amountToCollect;

    public CollectResourcesAction(Nation nation, int amountToCollect) {
        super(nation);
        this.amountToCollect = amountToCollect;
    }

    @Override
    public void execute(Nation targetNation) {
        nation.collectResources(amountToCollect);
        System.out.println(nation.getName() + " collected " + amountToCollect + " resources. Total resources: " + nation.getResources());
    }
}
