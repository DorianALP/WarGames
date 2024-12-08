public class Nation {

    private String name;
    private int health;
    private int numNukes;
    private int numSoldiers;
    private int resources;
    private int shieldStrength;

    public Nation(String name) {
        this.name = name;
        this.health = 100;         // Starting health
        this.numNukes = 0;         // Starting number of nukes
        this.numSoldiers = 0;      // Starting number of soldiers
        this.resources = 100;      // Starting resources
        this.shieldStrength = 100;   // Starting shield strength
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getNumNukes() {
        return numNukes;
    }

    public int getNumSoldiers() {
        return numSoldiers;
    }

    public int getResources() {
        return resources;
    }

    public int getShieldStrength() {
        return shieldStrength;
    }

    // Method to build a nuke
    public String buildNuke() {
        int nukeCost = 50;  // Cost to build a nuke
        if (resources >= nukeCost) {
            numNukes++;
            resources -= nukeCost;
            return name + " built a nuke. Total nukes: " + numNukes;
        } else {
            return name + " doesn't have enough resources to build a nuke.";
        }
    }

    // Method to recruit soldiers
    public String recruitSoldiers(int number) {
        int soldierCost = 10;  // Cost per soldier
        int totalCost = number * soldierCost;
        if (resources >= totalCost) {
            numSoldiers += number;
            resources -= totalCost;
            return name + " recruited " + number + " soldiers. Total soldiers: " + numSoldiers;
        } else {
            return name + " doesn't have enough resources to recruit soldiers.";
        }
    }

    // Method to launch a nuke at another nation
    public String launchNuke(Nation target) {
        if (numNukes > 0) {
            numNukes--;
            int damage = 50;  // Damage dealt by a nuke
            target.receiveDamage(damage);
            return name + " launched a nuke at " + target.getName();
        } else {
            return name + " has no nukes to launch.";
        }
    }

    // Method to deploy soldiers to attack another nation
    public String deploySoldiers(Nation target, int soldiersToDeploy) {
        if (numSoldiers >= soldiersToDeploy) {
            numSoldiers -= soldiersToDeploy;
            int damage = soldiersToDeploy * 2;  // Damage per soldier
            target.receiveDamage(damage);
            return name + " deployed " + soldiersToDeploy + " soldiers to attack " + target.getName();
        } else {
            return name + " doesn't have enough soldiers to deploy.";
        }
    }

    // Method to strengthen the nation's shield
    public String strengthenShield(int amount) {
        int shieldCost = 5;  // Cost per shield point
        int totalCost = amount * shieldCost;
        if (resources >= totalCost) {
            shieldStrength += amount;
            resources -= totalCost;
            return name + " increased shield strength by " + amount + ". Total shield: " + shieldStrength;
        } else {
            return name + " doesn't have enough resources to strengthen the shield.";
        }
    }

    // Method to collect resources
    public String collectResources(int amount) {
        resources += amount;
        return name + " collected " + amount + " resources. Total resources: " + resources;
    }

    // Method to handle receiving damage
    public String receiveDamage(int damage) {
        String ret = "";
        int effectiveDamage = damage;

        // Shield absorbs damage first
        if (shieldStrength > 0) {
            int shieldAbsorb = Math.min(shieldStrength, damage);
            shieldStrength -= shieldAbsorb;
            effectiveDamage -= shieldAbsorb;
            ret += name + "'s shield absorbed " + shieldAbsorb + " damage.\n";
        }

        // Remaining damage reduces health
        health -= effectiveDamage;
        if (health < 0) {
            health = 0;
        }

        ret += name + " took " + effectiveDamage + " damage. Remaining health: " + health;
        return ret;
    }

    // Method to check if the nation is defeated
    public boolean isDefeated() {
        return health <= 0;
    }
}
