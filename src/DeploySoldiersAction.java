class DeploySoldiersAction extends GameAction {
    private Nation target;
    private int soldiersToDeploy;

    public DeploySoldiersAction(Nation nation, Nation target, int soldiersToDeploy) {
        super(nation);
        this.target = target;
        this.soldiersToDeploy = soldiersToDeploy;
    }

    @Override
    public void execute(Nation targetNation) {
        nation.deploySoldiers(this.target, soldiersToDeploy);
        System.out.println(nation.getName() + " deployed " + soldiersToDeploy + " soldiers to " + target.getName() + ". " + nation.getName() + "total soldiers left: " + nation.getNumSoldiers());

    }
}