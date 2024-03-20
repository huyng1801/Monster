package monster;

import java.util.Random;

public abstract class Monster {

    private final String clanAffiliation;
    private int ferocity;
    private int defense;
    private int magic;
    private int treasure;
    private int health;

    public Monster(String clanAffiliation, int ferocity, int defense, int magic, int treasure, int health) {
        this.clanAffiliation = clanAffiliation;
        this.ferocity = validateAttribute(ferocity);
        this.defense = validateAttribute(defense);
        this.magic = validateAttribute(magic);
        this.treasure = (health > 0) ? treasure : 0;
        this.health = (health > 0) ? health : 0;
   
    }

    public Monster(String clanAffiliation) {
        this(clanAffiliation, getRandomAttribute(), getRandomAttribute(), getRandomAttribute(), 0, 100);
    }

    private int validateAttribute(int value) {
        return Math.max(0, Math.min(20, value));
    }

    private static int getRandomAttribute() {
        Random random = new Random();
        return random.nextInt(21);
    }

    public abstract double getBattleScore();

    public void attack(Monster opponent) {
        if (isDead() || opponent.isDead()) {
            System.out.println("Error: Dead monsters cannot attack or be attacked.");
            return;
        }

        double myScore = getBattleScore();
        double opponentScore = opponent.getBattleScore();
        Random random = new Random();
        int opponentTreasure = opponent.getTreasure();
        if (myScore > opponentScore) {
            double damage = myScore - opponentScore;
            opponent.takeDamage(damage);
            int takeTreasure = random.nextInt(opponentTreasure + 1);
            this.gainTreasure(takeTreasure);
            opponent.loseTreasure(takeTreasure);
        } else {
            double damage = opponentScore - myScore;
            this.takeDamage(damage);
            int takeTreasure = random.nextInt(opponentTreasure + 1);
            this.loseTreasure(takeTreasure);
            opponent.gainTreasure(takeTreasure);
        }

    }

    public void increaseAttribute(String attributeName) {
        switch (attributeName) {
            case "ferocity":
                setFerocity(getFerocity() + 1);
                break;
            case "defense":
                setDefense(getDefense() + 1);
                break;
            case "magic":
                setMagic(getMagic() + 1);
                break;
            default:
                System.out.println("Invalid attribute name.");
                break;
        }
    }

    public void decreaseAttribute(String attributeName) {
        switch (attributeName) {
            case "ferocity":
                setFerocity(getFerocity() - 1);
                break;
            case "defense":
                setDefense(getDefense() - 1);
                break;
            case "magic":
                setMagic(getMagic() - 1);
                break;
            default:
                System.out.println("Invalid attribute name.");
                break;
        }
    }

    public void gainTreasure(int amount) {
        if (isDead()) {
            System.out.println("Error: " + getFullName() + " is dead and cannot gain treasure.");
            return;
        }

        treasure += amount;
        System.out.println(getFullName() + " gained " + amount + " treasure.");
    }

    public void loseTreasure(int amount) {
        if (isDead()) {
            System.out.println("Error: " + getFullName() + " is dead and cannot lose treasure.");
            return;
        }

        int remainingTreasure = Math.max(0, treasure - amount);
        int lostAmount = treasure - remainingTreasure;
        treasure = remainingTreasure;
        System.out.println(getFullName() + " lost " + lostAmount + " treasure.");
    }

    public void takeDamage(double damage) {
        if (isDead()) {
            System.out.println("Error: " + getFullName() + " is already dead and cannot take more damage.");
            return;
        }

        int newHealth = Math.max(0, health - (int) damage);
        if (newHealth == 0) {
            System.out.println(getFullName() + " has been defeated.");
        }
        health = newHealth;
    }

    public boolean isDead() {
        return 0 == health;
    }

    public int getTreasure() {
        return treasure;
    }

    public String getFullName() {
        return getClass() + " from clan " + clanAffiliation;
    }

    public void setFerocity(int ferocity) {
        this.ferocity = validateAttribute(ferocity);
    }

    public void setDefense(int defense) {
        this.defense = validateAttribute(defense);
    }

    public void setMagic(int magic) {
        this.magic = validateAttribute(magic);
    }

    public int getFerocity() {
        return ferocity;
    }

    public int getDefense() {
        return defense;
    }

    public int getMagic() {
        return magic;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setTreasure(int treasure) {
        this.treasure = treasure;
    }

    public void takeHealing(int healthBoost) {
        if (isDead()) {
            System.out.println("Error: " + getFullName() + " is dead and cannot be healed.");
            return;
        }

        int currentHealth = getHealth();
        int newHealth = currentHealth + healthBoost;
        int healedAmount = newHealth - currentHealth; // Calculate the actual amount of healing
        setHealth(newHealth); // Update health after healing
        System.out.println(getFullName() + " was healed by " + healedAmount + " health points.");
    }

    @Override
    public String toString() {
        String status = isDead() ? "Dead" : "Alive";
        return getFullName() + ", Status: " + status + ", Ratings: Ferocity-" + ferocity + ", Defense-" + defense + ", Magic-" + magic + ", Treasure-" + treasure + ", Health-" + health;
    }
}
