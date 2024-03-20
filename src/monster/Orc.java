package monster;

import java.util.ArrayList;
import java.util.List;

public class Orc extends Monster {

    private boolean isWarlord;
    private int leadership;
    private List<Orc> infantry;
    private Orc commandingWarlord;

    public void soundBattleCry() {
        if (isDead()) {
            System.out.println("Error: " + getFullName() + " is dead and cannot sound the battle cry.");
            return;
        }

        if (!isWarlord()) {
            System.out.println("Error: " + getFullName() + " is not a Warlord and cannot sound the battle cry.");
            return;
        }

        int healthBoost = leadership * 5;

        if (infantry.isEmpty()) {
            System.out.println("Error: " + getFullName() + " has no infantry to heal.");
            return;
        }
        System.out.println(getFullName() + " is Warlord sounded the battle cry, healing " + infantry.size()
                + " Infantry by " + healthBoost + " health points each.");
        healInfantry(healthBoost);

    }

    public Orc(String clanAffiliation, int ferocity, int defense, int magic, int treasure, int health, boolean isWarlord) {
        super(clanAffiliation, ferocity, defense, magic, treasure, health);
        this.isWarlord = isWarlord;
    }

    public Orc(String clanAffiliation, int ferocity, int defense, int magic, int treasure, int health, boolean isWarlord, int leadership) {
        super(clanAffiliation, ferocity, defense, magic, treasure, health);
        this.isWarlord = isWarlord;
        this.leadership = Math.max(1, Math.min(5, leadership));
        this.infantry = new ArrayList<>();
    }

    public boolean isWarlord() {
        return isWarlord;
    }

    public int getLeadership() {
        return leadership;
    }

    public List<Orc> getInfantry() {
        return infantry;
    }

    public Orc getCommandingWarlord() {
        return commandingWarlord;
    }

    public void setLeadership(int leadership) {
        this.leadership = Math.max(1, Math.min(5, leadership));
    }

    public void setCommandingWarlord(Orc commandingWarlord) {
        this.commandingWarlord = commandingWarlord;
    }

    public void addInfantry(Orc orc) {
        if (isDead()) {
            System.out.println("Error: " + getFullName() + " is dead and cannot command Infantry.");
            return;
        }

        if (infantry.contains(orc)) {
            System.out.println(orc.getFullName() + " is already part of " + getFullName() + "'s Infantry.");
            return;
        }

        if (orc.isWarlord()) {
            System.out.println("Error: A Warlord cannot be part of another Warlord's Infantry.");
            return;
        }

        infantry.add(orc);
        orc.setCommandingWarlord(this);
    }

    public void removeInfantry(Orc orc) {
        if (isDead()) {
            System.out.println("Error: " + getFullName() + " is dead and cannot command Infantry.");
            return;
        }

        if (!infantry.contains(orc)) {
            System.out.println(orc.getFullName() + " is not part of " + getFullName() + "'s Infantry.");
            return;
        }

        infantry.remove(orc);
        orc.setCommandingWarlord(null);
    }

    public void healInfantry(int healthBoost) {
        if (isDead()) {
            System.out.println("Error: " + getFullName() + " is dead and cannot heal Infantry.");
            return;
        }

        for (Orc orc : infantry) {
            if (!orc.isDead()) {
                orc.takeHealing(healthBoost);
            }
        }
    }

    @Override
    public void gainTreasure(int amount) {
        if (isDead()) {
            System.out.println("Error: " + getFullName() + " is dead and cannot gain treasure.");
            return;
        }

        setTreasure(getTreasure() + amount);

        // If the orc is a warlord, update the leadership with the treasure bonus
        if (isWarlord) {
            int leadershipBoost = amount / 10;
            setLeadership(getLeadership() + leadershipBoost);
            System.out.println(getFullName() + " gained " + amount + " treasure and received a leadership boost of +" + leadershipBoost);
        } else {
            System.out.println(getFullName() + " gained " + amount + " treasure.");
        }
    }

    @Override
    public double getBattleScore() {
        double average = (getFerocity() + getDefense() + getMagic()) / 3.0;
        return isWarlord ? average * 1.5 : average;
    }

    @Override
    public String toString() {
        String warlordInfo = isWarlord ? (", Warlord, Leadership: " + leadership) : "";
        String infantryCount = isWarlord ? (", Infantry Count: " + infantry.size()) : "";
        return super.toString() + warlordInfo + infantryCount;
    }
}
