package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Kassandra Smith>
 * <KSS2474>
 * <15465>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Spring 2018
 */

/**
 * One of two required Critters. Named Critter2 in accordance with project instructions
 * Critter2 is a "pacifist" ... she'll fight at random. She also has an "always forward" mentality and only moves in one direction.
 */
public class Critter2 extends Critter {
    int fight = 0;

    /**
     * Constructor
     */
    public Critter2() {
        fight = (Critter.getRandomInt(10) % 2);
    }

    /**
     * Only walks "forward" and then has a child
     */
    @Override
    public void doTimeStep() {
        walk(0);
        Critter2 baby = new Critter2();
        reproduce(baby, 0);
    }

    /**
     * Randomly determines to fight
     *
     * @param opponent the other Critter in the space
     * @return
     */
    @Override
    public boolean fight(String opponent) {
        if (fight == 0) {
            return true;
        }
        return false;
    }

    /**
     * @return representation of the critter
     */
    public String toString() {
        return "2";
    }
}
