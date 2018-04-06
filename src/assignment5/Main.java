package assignment5;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */

/**
 * Main class
 */
public class Main extends Application implements EventHandler {
    Tab makeTab = new Tab();
    Tab runTab = new Tab();
    Tab animateTab = new Tab();
    Tab seedTab = new Tab();
    Tab statsTab = new Tab();
    Tab quitTab = new Tab();
    ArrayList<Critter> crits;
    static AnchorPane critterWorld = new AnchorPane();
    TextArea stats = new TextArea();
    private static String myPackage;    // package of Critter file.  Critter cannot be in default pkg.

    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * The main function
     *
     * @param args required input
     * @throws InvalidCritterException for any exception
     */
    public static void main(String[] args) throws InvalidCritterException {
        launch(args);

    }

    /**
     * Sets up the gui
     *
     * @param stage the stage to be built on
     */
    @Override
    public void start(Stage stage) {
        crits = getCritters();
        BorderPane border = new BorderPane();
        TabPane tabPane = new TabPane();
        tabPane.setMinWidth(300);
        tabPane.setMinHeight(400);
        double height = stage.getHeight();
        double width = stage.getWidth();
        stage.minWidthProperty().setValue(800);
        stage.minHeightProperty().setValue(450);
        stage.setTitle("Critters Part Two");
        Group root = new Group();
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add("assignment5/gui.css");
        tabPane = setTabs(tabPane);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        stats.setDisable(true);
        stats.setMaxHeight(100);
        stats.setMinHeight(100);
        border.setRight(tabPane);
        border.setCenter(critterWorld);
        critterWorld.setMaxWidth(1600);
        border.setBottom(stats);
        border.prefHeightProperty().bind(scene.heightProperty());
        border.prefWidthProperty().bind(scene.widthProperty());
        Critter.displayWorld(critterWorld);
        root.getChildren().add(border);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    /**
     * Make the tabs for the gui
     *
     * @param tabPane the tabPane we add our panes to
     * @return a nice, populated tabPane
     */
    private TabPane setTabs(TabPane tabPane) {
        makeTab.setText("Make");
        runTab.setText("Run");
        animateTab.setText("Animate");
        seedTab.setText("Seed");
        statsTab.setText("Stats");
        quitTab.setText("Quit");
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        makeTab.setContent(hbox);
        tabPane.getTabs().add(buildMakeTab(makeTab));
        tabPane.getTabs().add(buildAnimateTab(animateTab));
        tabPane.getTabs().add(buildRunTab(runTab));
        tabPane.getTabs().add(buildSeedTab(seedTab));
        tabPane.getTabs().add(buildStatsTab(statsTab));
        tabPane.getTabs().add(buildQuitTab(quitTab));
        return tabPane;
    }

    /**
     * builds the tab that quits
     *
     * @param quitTab the reference to the tab we build
     * @return a built tab
     */
    private Tab buildQuitTab(Tab quitTab) {
        FlowPane fp = new FlowPane(Orientation.VERTICAL);
        fp.setPrefWrapLength(150);
        fp.setPadding(new Insets(10));
        fp.setVgap(25);
        Button b = new Button("Quit");
        Button b2 = new Button("Restart");
        fp.getChildren().addAll(b, b2);
        quitTab.setContent(fp);
        //        fp.getChildren().addAll(b);
        //        quitTab.setContent(fp);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Stage stage = (Stage) b.getScene().getWindow();
                    stage.close();
                } catch (Exception e) {
                }
            }
        });
        b2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Critter.clearWorld();
                    Critter.displayWorld(critterWorld);
                } catch (Exception e) {
                }
            }
        });
        return quitTab;

    }

    /**
     * builds the tab that shows stats
     *
     * @param statsTab the reference to the tab we build
     * @return a built tab
     */
    private Tab buildStatsTab(Tab statsTab) {
        FlowPane fp = new FlowPane(Orientation.VERTICAL);
        fp.setPrefWrapLength(150);
        fp.setPadding(new Insets(10));
        fp.setVgap(25);
        Text t = new Text("Select a Critter");
        ComboBox critterOptions = new ComboBox();
        critterOptions.getItems().addAll(crits);
        critterOptions.getSelectionModel().selectFirst();
        critterOptions.setEditable(false);
        Button b = new Button("Stats");
        fp.getChildren().addAll(t, critterOptions, b);
        statsTab.setContent(fp);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String s;
                    Class<Critter> c = (Class<Critter>) Class.forName(myPackage + "." + critterOptions.getValue());
                    Method method = c.getMethod("runStats", List.class);
                    s = (String) method.invoke(c, Critter.getInstances((String) critterOptions.getValue()));
                    stats.appendText(s);
                    stats.appendText("\n");
                } catch (Exception e) {
                    critterOptions.setPromptText("invalid input");
                }
            }
        });
        return statsTab;
    }

    /**
     * builds the tab that makes Critters
     *
     * @param makeTab the reference to the tab we build
     * @return a built tab
     */
    private Tab buildMakeTab(Tab makeTab) {
        FlowPane fp = new FlowPane(Orientation.VERTICAL);
        fp.setPrefWrapLength(150);
        fp.setPadding(new Insets(10));
        fp.setVgap(25);
        Text t = new Text("Select a Critter");
        ComboBox critterOptions = new ComboBox();
        critterOptions.getItems().addAll(crits);
        critterOptions.getSelectionModel().selectFirst();
        critterOptions.setEditable(false);
        //        String value = (String) critterOptions.getValue();
        TextField numOf = new TextField();
        numOf.setPromptText("Amount");
        Button b = new Button("Make");
        fp.getChildren().addAll(t, critterOptions, numOf, b);
        makeTab.setContent(fp);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    InputValues.makeWhichCritter = (String) critterOptions.getValue();
                    InputValues.amtOfCritters = Integer.parseInt(numOf.getText());
                    for (int i = 0; i < InputValues.amtOfCritters; i++) {
                        Critter.makeCritter(InputValues.makeWhichCritter);
                    }
                    critterWorld.getChildren().clear();
                    Critter.displayWorld(critterWorld);
                    //                    System.out.println("Debug:" + critterWorld.getWidth());
                } catch (Exception e) {
                    numOf.setPromptText("invalid input");
                }
            }
        });
        //
        return makeTab;
    }

    /**
     * builds the tab that runs
     *
     * @param runTab the reference to the tab we build
     * @return a built tab
     */
    private Tab buildRunTab(Tab runTab) {
        FlowPane fp = new FlowPane(Orientation.VERTICAL);
        fp.setPrefWrapLength(150);
        fp.setPadding(new Insets(10));
        fp.setVgap(25);
        TextField numOf = new TextField();
        numOf.setPromptText("Number of steps to run");
        Button b = new Button("Run");
        //Button b2 = new Button("Stop"); // todo, Do we need this here?
        fp.getChildren().addAll(numOf, b);
        runTab.setContent(fp);
        //
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    InputValues.runTimeStep = new Integer(Integer.parseInt(numOf.getText()));
                    for (int i = 0; i < InputValues.runTimeStep; i++) {
                        Critter.worldTimeStep();
                    }
                    critterWorld.getChildren().clear();
                    Critter.displayWorld(critterWorld);
                } catch (Exception e) {
                    numOf.setPromptText("invalid input");
                }
            }
        });
        return runTab;
    }

    /**
     * builds the tab that runs animation
     *
     * @param animateTab the reference to the tab we build
     * @return a built tab
     */
    private Tab buildAnimateTab(Tab animateTab) {
        FlowPane fp = new FlowPane(Orientation.VERTICAL);
        fp.setPrefWrapLength(150);
        fp.setPadding(new Insets(10));
        fp.setVgap(25);
        ComboBox animateOptions = new ComboBox();
        animateOptions.getItems().addAll("1", "5", "10", "20", "25");
        animateOptions.getSelectionModel().selectFirst();
        animateOptions.setEditable(false);
        Button b = new Button("Start"); // todo: maybe change to one button that toggles its state
        Button b2 = new Button("Stop");
        b2.setDisable(true);
        fp.getChildren().addAll(animateOptions, b, b2);
        animateTab.setContent(fp);
        //
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame timeStep = new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i = 0; i < InputValues.animateTimeStep; i++) {
                    PauseTransition pauseTransition = new PauseTransition(Duration.seconds(i));
                    pauseTransition.setOnFinished(e -> Critter.worldTimeStep());
                    pauseTransition.play();
                }
                critterWorld.getChildren().clear();
                Critter.displayWorld(critterWorld);
            }
        });
        timeline.getKeyFrames().add(timeStep);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Black out all unnecessary inputs
                b.setDisable(true);
                b2.setDisable(false);
                makeTab.setDisable(true);
                runTab.setDisable(true);
                seedTab.setDisable(true);
                statsTab.setDisable(true);
                //
                InputValues.animateTimeStep = Integer.parseInt((String) animateOptions.getValue());
                timeline.play();
            }
        });
        b2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                b.setDisable(false);
                b2.setDisable(true);
                makeTab.setDisable(false);
                runTab.setDisable(false);
                seedTab.setDisable(false);
                statsTab.setDisable(false);
                timeline.stop();
            }
        });
        //
        return animateTab;
    }

    /**
     * builds the tab that seeds
     *
     * @param seedTab the reference to the tab we build
     * @return a built tab
     */
    private Tab buildSeedTab(Tab seedTab) {
        FlowPane fp = new FlowPane(Orientation.VERTICAL);
        fp.setPrefWrapLength(150);
        fp.setPadding(new Insets(10));
        fp.setVgap(25);
        TextField seed = new TextField();
        seed.setPromptText("Seed number");
        Button b = new Button("Set");
        fp.getChildren().addAll(seed, b);
        seedTab.setContent(fp);
        //
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    InputValues.seedNumber = Integer.parseInt(seed.getText());
                    Critter.setSeed(InputValues.seedNumber);
                } catch (Exception e) {
                    seed.setPromptText("invalid input");
                }
            }
        });
        //
        return seedTab;
    }

    /**
     * Gets the types of Critters in the folder
     *
     * @return the Critter types
     */
    public ArrayList<Critter> getCritters() {
        ArrayList critterTypes = new ArrayList<String>();
        ArrayList notCritters = new ArrayList();
        File[] files = new File("src/" + Critter.class.getPackage().toString().split(" ")[1]).listFiles();
        for (File file : files) {
            if (file.isFile() && file.toString().contains(".java")) {
                critterTypes.add(file.getName().split("\\.")[0]);
            }

        }
        int size = critterTypes.size();
        for (int i = 0; i < size; i++) {
            try {
                Class<?> mycritter = Class.forName(Critter.class.getPackage().toString().split(" ")[1] + "." + critterTypes.get(i));
                if (!Class.forName(Critter.class.getPackage().toString().split(" ")[1] + ".Critter").isAssignableFrom(mycritter)) {
                    notCritters.add(critterTypes.get(i));
                }
            } catch (Exception e) {
                //                System.out.println("debug");
            }
        }
        notCritters.add("Critter");
        notCritters.add("Header");
        critterTypes.removeAll(notCritters);
        return critterTypes;
    }

    /**
     * Required handler: Events are handled in their tabs
     *
     * @param event
     */
    @Override
    public void handle(Event event) {
    }
}
