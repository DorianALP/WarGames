public class RecruitSoldiersAction extends GameAction {
    private int numberToRecruit;

    public RecruitSoldiersAction(Nation nation, int numberToRecruit) {
        super(nation);
        this.numberToRecruit = numberToRecruit;
    }

    // targetNation not applicable in this action, can be null;
    @Override
    public String execute(Nation targetNation) {
        return nation.recruitSoldiers(numberToRecruit);
    }
}
