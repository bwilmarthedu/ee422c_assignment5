package assignment5;

import javafx.scene.paint.Color;

/**
 * One of four required Critters. Named Critter4 in accordance with project instructions
 * Critter4 is a rock. It doesn't move.
 */
public class Critter4 extends Critter {
    int fight = 0;
    int babyCount = 0;

    /**
     * Constructor
     */
    public Critter4() {
    }

    /**
     * If it manages to win some fights, it'll reproduce... very rarely
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
     * @param opponent the critter to fight
     * @return true because we fight everything
     */
    @Override
    public boolean fight(String opponent) {
        return true;
    }

    /**
     * @return the shape of the rock (a triangle)
     */
    @Override
    public CritterShape viewShape() {
        return CritterShape.TRIANGLE;
    }

    /**
     * @return the color of Critter 4
     */
    @Override
    public javafx.scene.paint.Color viewFillColor() {
        return Color.DARKSEAGREEN;
    }

    /**
     * @return representation of the critter
     */
    public String toString() {
        return "4";
    }
}
