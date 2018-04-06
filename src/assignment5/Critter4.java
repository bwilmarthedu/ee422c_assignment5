package assignment5;
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

import javafx.scene.paint.Color;

/**
 * One of four required Critters. Named Critter4 in accordance with project instructions
 * Critter4 is a "pacifist" ... she'll fight at random. She also has an "always forward" mentality and only moves in one direction.
 */
public class Critter4 extends Critter {
    int fight = 0;
    int babyCount = 0;

    /**
     * Constructor
     */
    public Critter4() {
        //        Params.min_reproduce_energy = 0;
        //        Params.refresh_algae_count = 0;
    }

    /**
     * Only walks "forward" and then has a child
     */
    @Override
    public void doTimeStep() {
        for (int dir = 8; dir > 0; dir--) {
            if (this.look(dir, false) == null && babyCount < 3 && this.getEnergy() > 150) {
                Critter4 baby = new Critter4();
                reproduce(baby, dir);
                babyCount++;
            }
        }

    }

    /**
     * Hates Craig
     *
     * @param opponent the other Critter in the space
     * @return
     */
    @Override
    public boolean fight(String opponent) {
        if (opponent.equals("C")) {
            return true; //always fight Craig
        } else {
            return false;
        }
    }

    @Override
    public CritterShape viewShape() {
        return CritterShape.TRIANGLE;
    }

    @Override
    public javafx.scene.paint.Color viewOutlineColor() {
        return Color.DARKSEAGREEN;
    }

    /**
     * @return representation of the critter
     */
    public String toString() {
        return "4";
    }
}
