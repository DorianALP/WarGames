class LaunchNukeAction extends GameAction {
    private Nation target;

    public LaunchNukeAction(Nation nation, Nation target) {
        super(nation);
        this.target = target;
    }

    @Override
    public String execute(Nation targetNation) {
        return nation.launchNuke(this.target);
    }
}