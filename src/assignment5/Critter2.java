package assignment5;

import javafx.scene.paint.Color;

public class Critter2 extends Critter {
    @Override
    public String toString() {
        return "C";
    }

    private static final int GENE_TOTAL = 24;
    private int[] genes = new int[8];
    private int dir;

    public Critter2() {
        for (int k = 0; k < 8; k += 1) {
            genes[k] = GENE_TOTAL / 8;
        }
        dir = Critter.getRandomInt(8);
    }

    public boolean fight(String not_used) {
        return true;
    }

    @Override
    public void doTimeStep() {
        /* Move to somewhere without algae */
        for (int dir = 0; dir < 8; dir++) {
            if(this.look(dir, false) != null) {
                if (this.look(dir, false).equals("@")) {
                    walk(dir);
                    return;
                }
            }
        }
        for (int dir = 0; dir < 8; dir++) {
            if(this.look(dir, true) != null) {
                if (this.look(dir, true).equals("@")) {
                    run(dir);
                    return;
                }
            }
        }
    }

    //todo: given to us as public static void, however the project requires runstats to return a String
    public static String runStats(java.util.List<Critter> craigs) {
        return "Omnomnomnom";
    }

    @Override
    public CritterShape viewShape() {
        return CritterShape.STAR;
    }

    @Override
    public javafx.scene.paint.Color viewOutlineColor() {
        return Color.PURPLE;
    }

}
