package monster;
public class Manticore extends Monster {
    private String currentClan;

    public Manticore(String clanAffiliation, int ferocity, int defense, int magic, int treasure, int health) {
        super(clanAffiliation, ferocity, defense, magic, treasure, health);
        this.currentClan = clanAffiliation;
    }

    public Manticore(String clanAffiliation) {
        super(clanAffiliation);
        this.currentClan = clanAffiliation;
    }

    public void changeClan(String newClan) {
        if (isDead()) {
            System.out.println("Error: " + getFullName() + " is dead and cannot change clans.");
            return;
        }

        currentClan = newClan;
    }

    @Override
    public double getBattleScore() {
        double average = (getFerocity() + getDefense() + getMagic()) / 3.0;
        return average * 1.5;
    }

    @Override
    public String toString() {
        return super.toString() + ", Current Clan: " + currentClan;
    }
}
