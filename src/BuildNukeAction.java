class BuildNukeAction extends GameAction {

    public BuildNukeAction(Nation nation) {
        super(nation);
    }

    @Override
    public void execute(Nation target) {
        nation.buildNuke();
        System.out.println(nation.getName() + " built a nuke!");

    }
}