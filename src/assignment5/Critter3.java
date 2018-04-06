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

import java.util.Objects;

/**
 * One of four required Critters. Named Critter3 in accordance with instructions
 * Only walks in one of two directions
 */
public class Critter3 extends Critter {
    int dir = 0;
    int babyCount = 0;

    public Critter3() {
        dir = Critter.getRandomInt(8);
    }

    public boolean fight(String not_used) {
        if (this.look(dir, false) != null) {
            walk(dir);
            return false;
        }
        return true;
    }

    @Override
    public void doTimeStep() {
        walk(dir % 2);
        if (getEnergy() > 100) {
            Critter3 child = new Critter3();
            reproduce(child, Critter.getRandomInt(8));
        }
    }


    @Override
    public CritterShape viewShape() {
        return CritterShape.DIAMOND;
    }

    @Override
    public javafx.scene.paint.Color viewColor() {
        return Color.CORAL;
    }

    /**
     * @return representation of the critter
     */
    public String toString() {
        return "3";
    }


    /**
     * Overriding run stats output
     *
     * @param critter1s all the critter1s
     * @return the string
     */
    public static String runStats(java.util.List<Critter> critter1s) {
        StringBuilder s = new StringBuilder();
        s.append("Why can't I turn!?");
        return s.toString();
    }
}


