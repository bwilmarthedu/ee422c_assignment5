package assignment5;

import java.util.List;

/**
 * Our workaround for private access components
 */
public class CritterWorld {

    /**Flag set for if the critter is fighting */
    public static int fightMode;
    /**The world of critters */
    public static Critter[][] critterWorld = new Critter[(int) Params.world_width][(int) Params.world_height];
    /**The world state before time step */
    public static Critter[][] oldWorld = new Critter[(int) Params.world_width][(int) Params.world_height];
    /**Flag set for if the critter is fighting */
    public static List<Critter> population = new java.util.ArrayList<Critter>();
    /**Flag set for if the critter is fighting */
    public static List<Critter> babies = new java.util.ArrayList<Critter>();
}
