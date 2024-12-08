class LaunchNukeAction extends GameAction {
    private Nation target;

    public LaunchNukeAction(Nation nation, Nation target) {
        super(nation);
        this.target = target;
    }

    @Override
    public void execute(Nation targetNation) {
        nation.launchNuke(this.target);
        System.out.println(nation.getName() + " launched a nuke at " + target.getName());

    }
}