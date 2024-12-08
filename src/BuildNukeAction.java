class BuildNukeAction extends GameAction {

    public BuildNukeAction(Nation nation) {
        super(nation);
    }

    @Override
    public String execute(Nation target) {
        return nation.buildNuke();
    }
}