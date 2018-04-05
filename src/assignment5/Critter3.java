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

import assignment5.Critter;

/**
 * One of two required Critters. Named Critter1 in accordance with instructions
 * <p>
 * Critter1 is a vegetarian: She by default fights Algae. She also only walks/runs (randomly chosen) in the cardinal directions.
 */
public class Critter3 extends Critter {
    int dir = 0;

    /**
     * Constructor
     */
    public Critter3() {
        dir = Critter.getRandomInt(10) % 2;
    }

    /**
     * Runs/walks as its time step: runs if in a cardinal direction, walks if not.
     */
    @Override
    public void doTimeStep() {
        if (dir == 0) {
            run(dir);
        } else {
            walk(dir);
        }
    }
    /**
     * Decides to fight if it's with Algae
     * @param opponent the other Critter in the space
     * @return whether or not we fight
     */
    @Override
    public boolean fight(String opponent) {
        if (opponent.equals("@")) {
            return true; //always fight
        } else {
            return false;
        }
    }

    @Override
    public CritterShape viewShape() {
        return null;
    }

    /**
     * @return representation of the critter
     */
    public String toString() {
        return "1";
    }
}


