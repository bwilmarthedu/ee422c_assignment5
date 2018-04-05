package assignment5;

import java.util.List;

public class CritterWorld {

    public static int fightMode;
    public static Critter[][] critterWorld = new Critter[(int) Params.world_width][(int) Params.world_height];
    public static Critter[][] oldWorld = new Critter[(int) Params.world_width][(int) Params.world_height];
    public static List<Critter> population = new java.util.ArrayList<Critter>();
    public static List<Critter> babies = new java.util.ArrayList<Critter>();

    /*
    private static Critter[][] placeCritters(double w, double h) {
        Critter[][] critterWorld = new Critter[(int) Params.world_width][(int) Params.world_height];
        for (int x = 0; x < (int) Params.world_width; x++) {
            for (int y = 0; y < (int) Params.world_height; y++) {
                critterWorld[x][y] = null;
            }
        }
        for (Critter c : population) {
            critterWorld[(int) c.x_coord][(int) c.y_coord] = c; // todo made a change here
        }
        return critterWorld;
    }
    */

}
