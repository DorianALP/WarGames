class Computer implements iPlay {

    private Nation nation;

    public Computer(Nation nation) {
        this.nation = nation;
    }

    @Override
    public GameAction chooseAction() {
        // Implement AI logic to choose an action
        return null; // Placeholder
    }

    @Override
    public Nation getNation() {
        return this.nation;
    }
}