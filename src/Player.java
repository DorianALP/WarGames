class Player implements iPlay {

    private Nation nation;

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