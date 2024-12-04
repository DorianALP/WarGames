class Player implements iPlay {

    private Nation nation;

    private static final int MAX_ACTIONS = 2;

    public Player(Nation nation) {
        this.nation = nation;
    }

    @Override
    public GameAction chooseAction() {
        // Implement logic for the player to choose an action
        return null; // Placeholder
    }

    @Override
    public Nation getNation() {
        return this.nation;
    }
}