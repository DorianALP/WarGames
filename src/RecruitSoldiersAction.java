public class RecruitSoldiersAction extends GameAction {
    private int numberToRecruit;

    public RecruitSoldiersAction(Nation nation, int numberToRecruit) {
        super(nation);
        this.numberToRecruit = numberToRecruit;
    }

    // targetNation not applicable in this action, can be null;
    @Override
    public void execute(Nation targetNation) {
        nation.recruitSoldiers(numberToRecruit);
        System.out.println(nation.getName() + " recruited " + numberToRecruit + " soldiers. Total soldiers: " + nation.getNumSoldiers());
    }
}
