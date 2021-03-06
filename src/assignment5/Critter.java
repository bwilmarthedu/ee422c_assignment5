package assignment5;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.max;
import static java.lang.Math.floor;
import static java.lang.Math.min;

public abstract class Critter {
    /* NEW FOR PROJECT 5 */
    public enum CritterShape {
        CIRCLE,
        SQUARE,
        TRIANGLE,
        DIAMOND,
        STAR
    }

    private int x_coord;
    private int y_coord;
    private int energy = 0;

    protected int getEnergy() {
        return energy;
    }

    /* the default color is white, which I hope makes critters invisible by default
     * If you change the background color of your View component, then update the default
     * color to be the same as you background
     *
     * critters must override at least one of the following three methods, it is not
     * proper for critters to remain invisible in the view
     *
     * If a critter only overrides the outline color, then it will look like a non-filled
     * shape, at least, that's the intent. You can edit these default methods however you
     * need to, but please preserve that intent as you implement them.
     */
    public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.WHITE;
    }

    public javafx.scene.paint.Color viewOutlineColor() {
        return viewColor();
    }

    public javafx.scene.paint.Color viewFillColor() {
        return viewColor();
    }

    public abstract CritterShape viewShape();

    private static String myPackage;
    //public static List<Critter> population = new java.util.ArrayList<Critter>();
    //private static List<Critter> babies = new java.util.ArrayList<Critter>();

    // Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * The look function sees whether or not there is a critter in the requested direction
     *
     * @param direction the direction to check
     * @param steps     the number of steps (1, 2) to check
     * @return the String of the critter in that direction (if there is one)
     */
    protected final String look(int direction, boolean steps) {
        String s = null;
        this.energy -= Params.look_energy_cost;
        int distance;
        int x = this.x_coord;
        int y = this.y_coord;
        if (steps == false) {
            distance = 1;
        } else {
            distance = 2;
        }
        ;
        switch (direction) {
            case 0:
                x = (x_coord + distance) % Params.world_width;
                break;
            case 1:
                x = (x_coord + distance) % Params.world_width;
                if (y_coord - distance < 0) {
                    y = y_coord - distance + Params.world_height;
                } else {
                    y -= distance;
                }
                break;
            case 2:
                if (y_coord - distance < 0) {
                    y = y_coord - distance + Params.world_height;
                } else {
                    y -= distance;
                }
                break;
            case 3:
                if (x_coord - distance < 0) {
                    x = x_coord - distance + Params.world_width;
                } else {
                    x -= distance;
                }
                if (y_coord - distance < 0) {
                    y = y_coord - distance + Params.world_height;
                } else {
                    y -= distance;
                }
                break;
            case 4:
                if (x_coord - distance < 0) {
                    x = x_coord - distance + Params.world_width;
                } else {
                    x -= distance;
                }
                break;
            case 5:
                if (x_coord - distance < 0) {
                    x = x_coord - distance + Params.world_width;
                } else {
                    x -= distance;
                }
                y = (y_coord + distance) % Params.world_height;
                break;
            case 6:
                y = (y_coord + distance) % Params.world_height;
                break;
            case 7:
                x = (x_coord + distance) % Params.world_width;
                y = (y_coord + distance) % Params.world_height;
                break;
        }
        if (CritterWorld.oldWorld[x][y] != null && CritterWorld.fightMode == 0) {
            return CritterWorld.oldWorld[x][y].toString();
        } else if (CritterWorld.critterWorld[x][y] != null && CritterWorld.fightMode == 1) {
            return CritterWorld.critterWorld[x][y].toString();
        }
        return null;
        //todo later: handle look during doTimeStep vs. look during run
    }

    /* rest is unchanged from Project 4 */
    private static java.util.Random rand = new java.util.Random();

    public static int getRandomInt(int max) {
        return rand.nextInt(max);
    }

    public static void setSeed(long new_seed) {
        rand = new java.util.Random(new_seed);
    }

    /* a one-character long string that visually depicts your critter in the ASCII interface */
    public String toString() {
        return "";
    }

    /**
     * Moves one space in a given direction and deducts Params.walk_energy_cost from the Critter.
     *
     * @param direction direction to move
     */
    protected final void walk(int direction) {
        move(direction, 1);
        energy -= Params.walk_energy_cost;
    }

    /**
     * Moves two spaces in a given direction and deducts Params.run_energy_cost from the Critter.
     *
     * @param direction direction to move two spaces
     */
    protected final void run(int direction) {
        move(direction, 2);
        energy -= Params.run_energy_cost;
    }

    /**
     * Moves a specified distance in a given direction
     *
     * @param direction direction to move
     * @param distance  how far to move
     */
    private final void move(int direction, int distance) {
        switch (direction) {
            case 0:
                x_coord = (x_coord + distance) % Params.world_width;
                break;
            case 1:
                x_coord = (x_coord + distance) % Params.world_width;
                if (y_coord - distance < 0) {
                    y_coord = y_coord - distance + Params.world_height;
                } else {
                    y_coord -= distance;
                }
                break;
            case 2:
                if (y_coord - distance < 0) {
                    y_coord = y_coord - distance + Params.world_height;
                } else {
                    y_coord -= distance;
                }
                break;
            case 3:
                if (x_coord - distance < 0) {
                    x_coord = x_coord - distance + Params.world_width;
                } else {
                    x_coord -= distance;
                }
                if (y_coord - distance < 0) {
                    y_coord = y_coord - distance + Params.world_height;
                } else {
                    y_coord -= distance;
                }
                break;
            case 4:
                if (x_coord - distance < 0) {
                    x_coord = x_coord - distance + Params.world_width;
                } else {
                    x_coord -= distance;
                }
                break;
            case 5:
                if (x_coord - distance < 0) {
                    x_coord = x_coord - distance + Params.world_width;
                } else {
                    x_coord -= distance;
                }
                y_coord = (y_coord + distance) % Params.world_height;
                break;
            case 6:
                y_coord = (y_coord + distance) % Params.world_height;
                break;
            case 7:
                x_coord = (x_coord + distance) % Params.world_width;
                y_coord = (y_coord + distance) % Params.world_height;
                break;
        }
    }

    /**
     * This method places offspring adjacent to a Critter in the world at a specified direction.
     *
     * @param offspring child Critter to be placed
     * @param direction direction in which to place the child Critter
     */
    protected final void reproduce(Critter offspring, int direction) {
        if (this.energy > Params.min_reproduce_energy) {
            CritterWorld.babies.add(offspring);
            offspring.energy = (int) Math.floor(0.5 * this.energy);
            this.energy = (int) Math.ceil(0.5 * this.energy);
            switch (direction) {
                case 0:
                    offspring.x_coord = (this.x_coord + 1) % Params.world_width;
                    break;
                case 1:
                    offspring.x_coord = (this.x_coord + 1) % Params.world_width;
                    if (this.y_coord - 1 < 0) {
                        offspring.y_coord = this.y_coord - 1 + Params.world_height;
                    } else {
                        offspring.y_coord = this.y_coord - 1;
                    }
                    break;
                case 2:
                    if (this.y_coord - 1 < 0) {
                        offspring.y_coord = this.y_coord - 1 + Params.world_height;
                    } else {
                        offspring.y_coord = this.y_coord - 1;
                    }
                    break;
                case 3:
                    if (this.x_coord - 1 < 0) {
                        offspring.x_coord = this.x_coord - 1 + Params.world_width;
                    } else {
                        offspring.x_coord = this.x_coord - 1;
                    }
                    if (this.y_coord - 1 < 0) {
                        offspring.y_coord = this.y_coord - 1 + Params.world_height;
                    } else {
                        offspring.y_coord = this.y_coord - 1;
                    }
                    break;
                case 4:
                    if (this.x_coord - 1 < 0) {
                        offspring.x_coord = this.x_coord - 1 + Params.world_width;
                    } else {
                        offspring.x_coord = this.x_coord - 1;
                    }
                    break;
                case 5:
                    if (this.x_coord - 1 < 0) {
                        offspring.x_coord = this.x_coord - 1 + Params.world_width;
                    } else {
                        offspring.x_coord = this.x_coord - 1;
                    }
                    offspring.y_coord = (this.y_coord + 1) % Params.world_height;
                    break;
                case 6:
                    offspring.y_coord = (this.y_coord + 1) % Params.world_height;
                    break;
                case 7:
                    offspring.x_coord = (this.x_coord + 1) % Params.world_width;
                    offspring.y_coord = (this.y_coord + 1) % Params.world_height;
                    break;
            }
        }
    }

    public abstract void doTimeStep();

    public abstract boolean fight(String opponent);

    /**
     * This method simulates one time step of the world.
     */
    public static void worldTimeStep() {
        // 1. Increment Timestep
        // 2. doTimeSteps();
        CritterWorld.fightMode = 0;
        CritterWorld.oldWorld = placeCritters();
        for (Critter c : CritterWorld.population) {
            c.doTimeStep();
        }
        // 3. doEncounters();
        CritterWorld.fightMode = 1;
        doEncounters();
        removeDead();
        // 4. updateRestEnergy();
        for (Critter c : CritterWorld.population) {
            c.energy -= Params.rest_energy_cost;
        }
        removeDead();
        // 6. genAlgae();
        for (int i = 0; i < Params.refresh_algae_count; i++) {
            try {
                makeCritter("Algae");
            } catch (InvalidCritterException e) {
            }
        }
        // 7. moveBabies();
        CritterWorld.population.addAll(CritterWorld.babies);
        CritterWorld.babies.clear();
    }

    /**
     * Adds the critters to a pane for display
     *
     * @param pane the pane to alter
     */
    public static void displayWorld(Object pane) {
        AnchorPane pn = (AnchorPane) pane;
        double width = pn.getWidth();
        double height = pn.getHeight();
        double unit;
        double worldAspectRatio = (double) Params.world_width / Params.world_height;
        double windowAspectRatio = (double) width / height;
        if (worldAspectRatio > windowAspectRatio) {
            unit = (double) width / Params.world_width;
            height = unit * Params.world_height;
        } else {
            unit = (double) height / Params.world_height;
            width = unit * Params.world_width;
        }
        if (width != 0) {
            Critter[][] newWorld = placeCritters();
            for (int i = 0; i < Params.world_height; i++) {
                for (int j = 0; j < Params.world_width; j++) {
                    Critter crit = newWorld[j][i];
                    if (crit != null) {
                        switch (crit.viewShape()) {
                            case CIRCLE:
                                Circle circ = new Circle();
                                circ.setFill(crit.viewColor());
                                circ.setStroke(crit.viewOutlineColor());
                                circ.setStrokeWidth(1);
                                circ.setRadius(floor((unit / 2) - 1));
                                circ.setCenterX(((crit.x_coord + 0.5) * width) / Params.world_width);
                                circ.setCenterY(((crit.y_coord + 0.5) * height) / Params.world_height);
                                pn.getChildren().add(circ);
                                break;
                            case SQUARE:
                                Rectangle squ = new Rectangle();
                                squ.setFill(crit.viewColor());
                                squ.setStroke(crit.viewOutlineColor());
                                squ.setStrokeWidth(1);
                                squ.setWidth(floor(unit - 1));
                                squ.setHeight(floor(unit - 1));
                                squ.setX(((crit.x_coord) * width) / Params.world_width);
                                squ.setY(((crit.y_coord) * height) / Params.world_height);
                                pn.getChildren().add(squ);
                                break;
                            case DIAMOND:
                                Polygon dia = new Polygon();
                                dia.setFill(crit.viewColor());
                                dia.setStroke(crit.viewOutlineColor());
                                dia.setStrokeWidth(1);
                                dia.getPoints().addAll(
                                        ((((crit.x_coord + 0.5) * width) / Params.world_width) - 1), ((crit.y_coord) * height) / Params.world_height,
                                        ((crit.x_coord) * width) / Params.world_width, ((((crit.y_coord + 0.5) * height) / Params.world_height) - 1),
                                        ((((crit.x_coord + 0.5) * width) / Params.world_width) - 1), ((((crit.y_coord + 1) * height) / Params.world_height) - 2),
                                        ((((crit.x_coord + 1) * width) / Params.world_width) - 2), ((((crit.y_coord + 0.5) * height) / Params.world_height) - 1));
                                pn.getChildren().add(dia);
                                break;
                            case TRIANGLE:
                                Polygon tri = new Polygon();
                                tri.setStroke(crit.viewOutlineColor());
                                tri.setFill(crit.viewFillColor());
                                tri.setStrokeWidth(1);
                                tri.getPoints().addAll(
                                        ((((crit.x_coord + 0.5) * width) / Params.world_width) - 1), ((crit.y_coord) * height) / Params.world_height,
                                        ((crit.x_coord) * width) / Params.world_width, ((((crit.y_coord + 1) * height) / Params.world_height) - 2),
                                        ((((crit.x_coord + 1) * width) / Params.world_width) - 2), ((((crit.y_coord + 1) * height) / Params.world_height) - 2));
                                pn.getChildren().add(tri);
                                break;
                            case STAR:
                                Polygon Star = new Polygon();
                                Star.getPoints().addAll(
                                        ((width * (crit.x_coord + 0.00 + 0.5) / Params.world_width) - 0.0 - 1.0), ((height * (crit.y_coord - 0.50 + 0.5) / Params.world_height) + 1.0 - 1.0),
                                        ((width * (crit.x_coord + 0.10 + 0.5) / Params.world_width) - 0.2 - 1.0), ((height * (crit.y_coord - 0.10 + 0.5) / Params.world_height) + 0.2 - 1.0),
                                        ((width * (crit.x_coord + 0.50 + 0.5) / Params.world_width) - 1.0 - 1.0), ((height * (crit.y_coord - 0.10 + 0.5) / Params.world_height) + 0.2 - 1.0),
                                        ((width * (crit.x_coord + 0.25 + 0.5) / Params.world_width) - 0.5 - 1.0), ((height * (crit.y_coord + 0.10 + 0.5) / Params.world_height) - 0.2 - 1.0),
                                        ((width * (crit.x_coord + 0.35 + 0.5) / Params.world_width) - 0.7 - 1.0), ((height * (crit.y_coord + 0.50 + 0.5) / Params.world_height) - 1.0 - 1.0),
                                        ((width * (crit.x_coord + 0.00 + 0.5) / Params.world_width) + 0.0 - 1.0), ((height * (crit.y_coord + 0.25 + 0.5) / Params.world_height) - 0.5 - 1.0),
                                        ((width * (crit.x_coord - 0.35 + 0.5) / Params.world_width) + 0.7 - 1.0), ((height * (crit.y_coord + 0.50 + 0.5) / Params.world_height) - 1.0 - 1.0),
                                        ((width * (crit.x_coord - 0.25 + 0.5) / Params.world_width) + 0.5 - 1.0), ((height * (crit.y_coord + 0.10 + 0.5) / Params.world_height) - 0.2 - 1.0),
                                        ((width * (crit.x_coord - 0.50 + 0.5) / Params.world_width) + 1.0 - 1.0), ((height * (crit.y_coord - 0.10 + 0.5) / Params.world_height) + 0.2 - 1.0),
                                        ((width * (crit.x_coord - 0.10 + 0.5) / Params.world_width) + 0.2 - 1.0), ((height * (crit.y_coord - 0.10 + 0.5) / Params.world_height) + 0.2 - 1.0)
                                );
                                Star.setFill(crit.viewColor());
                                Star.setStroke(crit.viewOutlineColor());
                                pn.getChildren().add(Star);
                                break;
                        }
                    }
                }
            }
        }
    }

    private static Critter[][] placeCritters() {
        Critter[][] critterWorld = new Critter[(int) Params.world_width][(int) Params.world_height];
        for (int x = 0; x < (int) Params.world_width; x++) {
            for (int y = 0; y < (int) Params.world_height; y++) {
                critterWorld[x][y] = null;
            }
        }
        for (Critter c : CritterWorld.population) {
            critterWorld[(int) c.x_coord][(int) c.y_coord] = c; // todo made a change here
        }
        return critterWorld;
    }
    /* Alternate displayWorld, where you use Main.<pane> to reach into your
       display component.
	   // public static void displayWorld() {}
	*/

    /* create and initialize a Critter subclass
     * critter_class_name must be the name of a concrete subclass of Critter, if not
     * an InvalidCritterException must be thrown
     */

    /**
     * create and initialize a Critter subclass.
     * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
     * an InvalidCritterException must be thrown.
     * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
     * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
     * an Exception.)
     *
     * @param critter_class_name
     * @throws InvalidCritterException
     */
    public static void makeCritter(String critter_class_name) throws InvalidCritterException {
        try {
            Class c = Class.forName(myPackage + "." + critter_class_name);
            Critter cr = (Critter) c.newInstance();
            CritterWorld.population.add(cr);
            cr.x_coord = getRandomInt(Params.world_width);
            cr.y_coord = getRandomInt(Params.world_height);
            cr.energy = Params.start_energy;
        } catch (Exception e) {
            throw new InvalidCritterException(critter_class_name);
        }
    }

    /**
     * Gets a list of critters of a specific type.
     *
     * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
     * @return List of Critters.
     * @throws InvalidCritterException
     */
    public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
        List<Critter> result = new java.util.ArrayList<Critter>();
        try {
            Class c = Class.forName(myPackage + "." + critter_class_name);
            for (int i = 0; i < CritterWorld.population.size(); i++) {
                Critter tempCritter = CritterWorld.population.get(i);
                if (c.equals(tempCritter.getClass())) {
                    result.add(tempCritter);
                }
            }
        } catch (Exception e) {
            throw new InvalidCritterException(critter_class_name);
        }
        return result;
    }

    /**
     * Prints out how many Critters of each type there are on the board.
     *
     * @param critters List of Critters.
     */
    //todo "For this assignment, runStats returns a String, and is not a void method."
    public static String runStats(List<Critter> critters) {
        StringBuilder stats = new StringBuilder();
        stats.append(critters.size() + " critters as follows: ");
        java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
        for (Critter crit : critters) {
            String crit_string = crit.toString();
            Integer old_count = critter_count.get(crit_string);
            if (old_count == null) {
                critter_count.put(crit_string, 1);
            } else {
                critter_count.put(crit_string, old_count.intValue() + 1);
            }
        }
        String prefix = "";
        for (String s : critter_count.keySet()) {
            stats.append(prefix + s + ":" + critter_count.get(s));
            prefix = ", ";
        }
        return stats.toString();
    }

    /* the TestCritter class allows some critters to "cheat". If you want to
     * create tests of your Critter model, you can create subclasses of this class
     * and then use the setter functions contained here.
     *
     * NOTE: you must make sure thath the setter functions work with your implementation
     * of Critter. That means, if you're recording the positions of your critters
     * using some sort of external grid or some other data structure in addition
     * to the x_coord and y_coord functions, then you MUST update these setter functions
     * so that they correctup update your grid/data structure.
     */
    static abstract class TestCritter extends Critter {
        protected void setEnergy(int new_energy_value) {
            super.energy = new_energy_value;
        }

        protected void setX_coord(int new_x_coord) {
            super.x_coord = new_x_coord;
        }

        protected void setY_coord(int new_y_coord) {
            super.y_coord = new_y_coord;
        }

        protected int getX_coord() {
            return super.x_coord;
        }

        protected int getY_coord() {
            return super.y_coord;
        }

        /*
         * This method getPopulation has to be modified by you if you are not using the population
         * ArrayList that has been provided in the starter code.  In any case, it has to be
         * implemented for grading tests to work.
         */
        protected static List<Critter> getPopulation() {
            return CritterWorld.population;
        }

        /*
         * This method getBabies has to be modified by you if you are not using the babies
         * ArrayList that has been provided in the starter code.  In any case, it has to be
         * implemented for grading tests to work.  Babies should be added to the general population
         * at either the beginning OR the end of every timestep.
         */
        protected static List<Critter> getBabies() {
            return CritterWorld.babies;
        }
    }

    /**
     * Clear the world of all critters, dead and alive
     */
    public static void clearWorld() {
        CritterWorld.population.clear();
        CritterWorld.babies.clear();
    }

    /*HELPER FUNCTIONS*/
    /**
     * This method displays a rudimentary model of the world in System.out.
     */
    public static void displayWorld() {
        char[][] world = new char[Params.world_height + 2][Params.world_width + 2];
        // populate borders and inside
        world[0][0] = '+';
        world[0][Params.world_width + 1] = '+';
        world[Params.world_height + 1][0] = '+';
        world[Params.world_height + 1][Params.world_width + 1] = '+';
        for (int i = 1; i < Params.world_height + 1; i++) {
            world[i][0] = '|';
            world[i][Params.world_width + 1] = '|';
            for (int j = 1; j < Params.world_width + 1; j++) {
                world[i][j] = ' ';
            }
        }
        for (int j = 1; j < Params.world_width + 1; j++) {
            world[0][j] = '-';
            world[Params.world_height + 1][j] = '-';
        }
        // Populate with critters
        for (Critter c : CritterWorld.population) {
            world[c.y_coord + 1][c.x_coord + 1] = c.toString().charAt(0);
        }
        // Print world
        for (int i = 0; i < Params.world_height + 2; i++) {
            System.out.println(world[i]);
        }
    }

    /**
     * This method performs resolves all encounters that are occurring in the world.
     */
    private static void doEncounters() {
        List<Critter> crittersOnSquare = new ArrayList<Critter>();
        for (int i = 0; i < Params.world_height; i++) {
            for (int j = 0; j < Params.world_width; j++) {
                for (Critter c : CritterWorld.population) {
                    if (c.x_coord == j && c.y_coord == i && c.energy > 0) {
                        crittersOnSquare.add(c);
                    }
                }
                while (crittersOnSquare.size() > 1) {
                    // Pick two critters
                    Critter a = crittersOnSquare.remove(0);
                    Critter b = crittersOnSquare.remove(0);
                    // encounter
                    Critter winner = encounter(a, b);
                    crittersOnSquare.add(winner);
                }
                crittersOnSquare.clear();
            }
        }
    }

    /**
     * This method resolves one encounter between Critter a and Critter b
     *
     * @param a The first critter
     * @param b The second Critter
     * @return returns the critter that won the fight
     */
    private static Critter encounter(Critter a, Critter b) {
        boolean afight, bfight;
        int aroll, broll;
        afight = a.fight(b.toString());
        bfight = b.fight(a.toString());
        if (afight && a.energy>0) {
            aroll = getRandomInt(a.energy);
        } else {
            aroll = 0;
        }
        if (bfight && b.energy>0) {
            broll = getRandomInt(b.energy);
        } else {
            broll = 0;
        }
        if (aroll >= broll) {
            a.energy += b.energy / 2;
            b.energy = 0;
            return a;
        } else {
            b.energy += a.energy / 2;
            a.energy = 0;
            return b;
        }
    }

    /**
     * This method removes all Critters from the world that have less than or equal to 0 energy.
     */
    private static void removeDead() {
        for (int i = 0; i < CritterWorld.population.size(); i++) {
            Critter c = CritterWorld.population.get(i);
            if (c.energy <= 0) {
                CritterWorld.population.remove(c);
            }
        }
    }


}
