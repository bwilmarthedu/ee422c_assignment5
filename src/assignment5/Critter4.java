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

import assignment5.Critter;
import javafx.scene.paint.Color;

/**
 * One of two required Critters. Named Critter2 in accordance with project instructions
 * Critter2 is a "pacifist" ... she'll fight at random. She also has an "always forward" mentality and only moves in one direction.
 */
public class Critter4 extends Critter {
    int fight = 0;

    /**
     * Constructor
     */
    public Critter4() {
        fight = (Critter.getRandomInt(10) % 2);
    }

    /**
     * Only walks "forward" and then has a child
     */
    @Override
    public void doTimeStep() {
        walk(0);
        Critter4 baby = new Critter4();
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

    @Override
    public CritterShape viewShape() {
        return Critter.CritterShape.STAR;
    }

    public javafx.scene.paint.Color viewOutlineColor() {
        return Color.DARKORANGE;
    }

    /**
     * @return representation of the critter
     */
    public String toString() {
        return "2";
    }
}
