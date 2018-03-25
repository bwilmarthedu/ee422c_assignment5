package assignment5;

import java.util.List;
import java.util.Scanner;
import java.io.*;

/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */

/**
 * Main class
 */
public class Main {
    static Scanner kb;    // scanner connected to keyboard input, or input file
    private static String inputFile;    // input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;    // if test specified, holds all console output
    private static String myPackage;    // package of test_sample.Critter file.  test_sample.Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;    // if you want to restore output to console


    // Gets the package name.  The usage assumes that test_sample.Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     *
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name,
     *             and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));

            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        System.out.print("critters>");
        String input = kb.nextLine();
        while (pollInput(input)) {
            System.out.print("critters>");
            input = kb.nextLine();
        }
        /* Write your code above */
        System.out.flush();

    }

    /**
     * Reads the input from the keyboard and calls functions accordingly
     *
     * @param input the keyboard input
     * @return boolean: whether or not to continue asking for input
     */
    private static boolean pollInput(String input) {
        String[] command;
        command = input.trim().split("\\s+");
        int count = 0;
        boolean returnBool = true;
        switch (command[0]) {
            case "quit":
                return false;
            case "show":
                if (!(command.length == 1)) {
                    System.out.println("error processing: " + input);
                } else {
                    Critter.displayWorld();
                }
                break;
            case "step":
                if (command.length == 1) {
                    count = 1;
                    for (int i = 0; i < count; i++) {
                        Critter.worldTimeStep();
                    }
                } else if ((command.length == 2) && (command[1] != null)) {
                    if (!command[1].matches("-?\\d+(\\.\\d+)?")) {
                        System.out.println("error processing: " + input);
                    } else {
                        count = Integer.parseInt(command[1]);
                        for (int i = 0; i < count; i++) {
                            Critter.worldTimeStep();
                        }
                    }
                } else {
                    System.out.println("error processing: " + input);
                }
                break;
            case "seed":
                if ((command.length != 2) || !(command[1].matches("-?\\d+(\\.\\d+)?"))) {
                    System.out.println("error processing: " + input);
                    break;
                } else {
                    Critter.setSeed(Integer.parseInt(command[1]));
                }
                break;
                /*guaranteed that make <class_name> is given a valid classname*/
            case "make":
                try {
                    if ((command.length != 2) && (command.length != 3)) {
                        System.out.println("error processing: " + input);
                        break;
                    } else if (command.length == 3) {
                        if (!command[2].matches("-?\\d+(\\.\\d+)?")) {
                            System.out.println("error processing: " + input);
                        } else {
                            count = Integer.parseInt(command[2]);
                            for (int j = 0; j < count; j++) {
                                Critter.makeCritter(command[1]);
                            }
                        }
                    } else if (command.length == 2) {
                        Critter.makeCritter(command[1]);
                    }
                    if (DEBUG) {
                        System.out.println("Debug: " + count + "Critters created");
                    }
                } catch (Exception e) {
                    System.out.println("error processing: " + input);
                }
                break;
            case "stats":
                if (command.length != 2) {
                    System.out.println("error processing: " + input);
                    break;
                }
                try {
                    List<Critter> instances = Critter.getInstances(command[1]);
                    try {
                        java.lang.reflect.Method method; //from piazza answers
                        Class c = Class.forName("test_sample." + command[1]);
                        method = c.getDeclaredMethod("runStats", java.util.List.class);
                        method.invoke(c, instances);
                    } catch (Exception e) {
                        Critter.runStats(instances);
                        //                        System.out.println("ERROR");
                    }
                } catch (InvalidCritterException e) {
                    System.out.println(e);
                }
                break;
            default:
                System.out.println("invalid command: " + command[0]);
                break;
        }
        return returnBool;
    }
}
