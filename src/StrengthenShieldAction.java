class StrengthenShieldAction extends GameAction {
    private int amount;

    public StrengthenShieldAction(Nation nation, int amount) {
        super(nation);
        this.amount = amount;
    }

    @Override
    public String execute(Nation targetNation) {
        return nation.strengthenShield(amount);
    }
}