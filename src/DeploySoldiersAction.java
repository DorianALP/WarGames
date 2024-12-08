class DeploySoldiersAction extends GameAction {
    private Nation target;
    private int soldiersToDeploy;

    public DeploySoldiersAction(Nation nation, Nation target, int soldiersToDeploy) {
        super(nation);
        this.target = target;
        this.soldiersToDeploy = soldiersToDeploy;
    }

    @Override
    public String execute(Nation targetNation) {
        return nation.deploySoldiers(this.target, soldiersToDeploy);
    }
}