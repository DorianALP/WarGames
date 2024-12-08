class StrengthenShieldAction extends GameAction {
    private int amount;

    public StrengthenShieldAction(Nation nation, int amount) {
        super(nation);
        this.amount = amount;
    }

    @Override
    public void execute(Nation targetNation) {
        nation.strengthenShield(amount);
        System.out.println(nation.getName() + " strengthened their shield to " + nation.getShieldStrength());

    }
}