package monster;
import java.util.Objects;

public class Goblin extends Monster {
    private Goblin swornEnemy;

    public Goblin(String clanAffiliation, int ferocity, int defense, int magic, int treasure, int health, Goblin swornEnemy) {
        super(clanAffiliation, ferocity, defense, magic, treasure, health);
        this.swornEnemy = swornEnemy;
    }

    public Goblin(String clanAffiliation) {
        super(clanAffiliation);
        this.swornEnemy = null; // Default value, can be updated later using setter
    }

    public void setSwornEnemy(Goblin swornEnemy) {
        this.swornEnemy = swornEnemy;
    }

    public Goblin getSwornEnemy() {
        return swornEnemy;
    }

    @Override
    public double getBattleScore() {
        double average = (getFerocity() + getDefense() + getMagic()) / 3.0;
        return (isSwornEnemyAlive()) ? average : average * 1.5;
    }

    private boolean isSwornEnemyAlive() {
        return !swornEnemy.isDead();
    }

    @Override
    public String toString() {
        return super.toString() + ", Sworn Enemy: " + swornEnemy.getFullName();
    }
}
