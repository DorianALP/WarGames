class Computer implements iPlay {

    private Nation nation;

    private Player player;

    private boolean computerTurn = false;
    private boolean firstTurn = true;

    private static final int MAX_ACTIONS = 2; // Two turns per move

    public Computer(Nation nation) {
        this.nation = nation;
        firstTurn = true;
    }

    @Override
    public GameAction chooseAction() {
        int TURN_COUNT = 0;
        // Implement AI logic to choose an action
        while(computerTurn && TURN_COUNT < MAX_ACTIONS) {
            think();
            TURN_COUNT++;
        }
        computerTurn = false;
        return null; // Placeholder
    }

    @Override
    public Nation getNation() {
        return this.nation;
    }

    public GameAction think() {
        // Check if it's the start of the game
        if(firstTurn) {
            firstTurn = false;
            return new RecruitSoldiersAction(nation, 5);
        }

        // Computer health greater than or equal to player
        if(this.nation.getHealth() >= player.getNation().getHealth()) {
            if(this.nation.getNumNukes() > 0) {
                return new LaunchNukeAction(this.nation, player.getNation());
            } else if(this.nation.getNumNukes() == 0) {
                return new BuildNukeAction(this.nation);
            }
        }

        // Computer health less than players
        if(this.nation.getHealth() < player.getNation().getHealth()) {
            // Check shield strength
            if(this.nation.getShieldStrength() <= 25) {
                return new StrengthenShieldAction(nation, 15);
            }
        }

        return new RecruitSoldiersAction(nation, 5);
    }
}