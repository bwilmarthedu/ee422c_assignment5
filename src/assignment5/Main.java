package assignment5;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import static assignment5.Critter.displayWorld;
import static assignment5.Critter.getInstances;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */

/**
 * Main class
 */
public class Main extends Application implements EventHandler {
    private final int WIDTH = 1000;
    private final int HEIGHT = 1000;
    private Button make, runTimeSteps, animate, seed;
    AnchorPane critterWorld = new AnchorPane();
    TextArea stats = new TextArea();
    //todo scene for runStats

    public static void main(String[] args) throws InvalidCritterException {
        for (int k = 0; k < 25; k++) {
            Critter.makeCritter("Algae");
        }
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Critters Part Two");
        BorderPane border = new BorderPane();
        TabPane tabPane = new TabPane();
        setSize(stage, WIDTH, HEIGHT);
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        tabPane = setTabs(tabPane);
        critterWorld.setPrefSize(750, 750); //todo: math to resize.
        critterWorld.setPadding(new Insets(50));
        Critter.displayWorld(critterWorld);
        //        Button printTextBtn = new Button("Show Stats");
        //        printTextBtn.setOnAction(e -> stats.setText("TODO STATS HERE"));
        stats.setText("TODO STATS HERE");
        try {
            stats.setText(Critter.runStats(getInstances("Algae")));
        } catch (InvalidCritterException e) {
            e.printStackTrace();
        }
        stats.setDisable(true);
        stats.setMaxHeight(100);
        //        tabPane.setPrefWidth(333);
        border.setRight(tabPane);
        border.setCenter(critterWorld);
        border.setBottom(stats);
        //        border.setBottom(printTextBtn);
        // bind to take available space
        root.getChildren().add(border);
        stage.setScene(scene);
        stage.show();
    }

    private TabPane setTabs(TabPane tabPane) {
        Tab makeTab = new Tab();
        Tab runTab = new Tab();
        Tab animateTab = new Tab();
        Tab seedTab = new Tab();
        makeTab.setText("Make");
        runTab.setText("Run");
        animateTab.setText("Animate");
        seedTab.setText("Seed");
        //        tabPane.getTabs().add()
        HBox hbox = new HBox();
        //  todo      hbox.getChildren().add(tabName);
        hbox.setAlignment(Pos.CENTER);
        makeTab.setContent(hbox);
        tabPane.getTabs().add(buildMakeTab(makeTab));
        tabPane.getTabs().add(buildAnimateTab(animateTab));
        tabPane.getTabs().add(buildRunTab(runTab));
        tabPane.getTabs().add(buildSeedTab(seedTab));
        return tabPane;
    }

    private Tab buildMakeTab(Tab makeTab) {
        FlowPane fp = new FlowPane(Orientation.VERTICAL);
        fp.setPrefWrapLength(150);
        fp.setPadding(new Insets(10));
        fp.setVgap(25);
        Text t = new Text("Select a Critter");
        ComboBox critterOptions = new ComboBox();
        critterOptions.getItems().addAll("Craig", "Algae", "Algaephobic", "Critter1", "Critter2", "Critter3", "Critter4"); // Todo populate using reflection?
        critterOptions.setEditable(false);
        //String value = (String) critterOptions.getValue();
        TextField numOf = new TextField("Amount");
        Button b = new Button("Make");
        fp.getChildren().addAll(t, critterOptions, numOf, b);
        makeTab.setContent(fp);
        //
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    InputValues.makeWhichCritter = (String) critterOptions.getValue();
                    InputValues.amtOfCritters = Integer.parseInt(numOf.getText());
                    for(int i = 0; i < InputValues.amtOfCritters; i++) {
                        Critter.makeCritter(InputValues.makeWhichCritter);
                    }
                    Critter.displayWorld(critterWorld);
                }
                catch(Exception e){
                    critterOptions.setValue("invalid input");
                }
            }
        });
        //
        return makeTab;
    }

    private Tab buildRunTab(Tab runTab) {
        FlowPane fp = new FlowPane(Orientation.VERTICAL);
        fp.setPrefWrapLength(150);
        fp.setPadding(new Insets(10));
        fp.setVgap(25);
        TextField numOf = new TextField("Number of steps to run");
        Button b = new Button("Run");
        //Button b2 = new Button("Stop"); // todo, Do we need this here?
        fp.getChildren().addAll(numOf, b);
        runTab.setContent(fp);
        //
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    InputValues.runTimeStep = new Integer(Integer.parseInt(numOf.getText()));
                    for(int i = 0; i < InputValues.runTimeStep; i++) {
                        Critter.worldTimeStep();
                    }
                    Critter.displayWorld(critterWorld);
                }
                catch(Exception e){
                    numOf.setText("invalid input");
                }
            }
        });
        return runTab;
    }

    private Tab buildAnimateTab(Tab animateTab) {
        FlowPane fp = new FlowPane(Orientation.VERTICAL);
        fp.setPrefWrapLength(150);
        fp.setPadding(new Insets(10));
        fp.setVgap(25);
        ComboBox animateOptions = new ComboBox();
        animateOptions.getItems().addAll("5", "10", "20", "25");
        animateOptions.setEditable(false);
        Button b = new Button("Start"); // todo: maybe change to one button that toggles its state
        Button b2 = new Button("Stop");
        b2.setDisable(true);
        fp.getChildren().addAll(animateOptions, b, b2);
        animateTab.setContent(fp);
        //
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame timeStep = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(int i = 0; i < InputValues.animateTimeStep; i++){
                    Critter.worldTimeStep();
                }
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
                InputValues.animateTimeStep = Integer.parseInt((String) animateOptions.getValue());
                timeline.play();
            }
        });
        b2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                b.setDisable(false);
                b2.setDisable(true);
                timeline.stop();
            }
        });
        //
        return animateTab;
    }

    private Tab buildSeedTab(Tab seedTab) {
        FlowPane fp = new FlowPane(Orientation.VERTICAL);
        fp.setPrefWrapLength(150);
        fp.setPadding(new Insets(10));
        fp.setVgap(25);
        TextField seed = new TextField("Seed number");
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
                }
                catch(Exception e){
                    seed.setText("invalid input");
                }
            }
        });
        //
        return seedTab;
    }

    public VBox addVbox() {
        VBox vert = new VBox();
        vert.setPadding(new Insets(10));
        vert.setSpacing(8);
        Text title = new Text("Options");
        vert.getChildren().add(title);
        Hyperlink options[] = new Hyperlink[]{
                new Hyperlink("Make"),
                new Hyperlink("Run"),
                new Hyperlink("Animate"),
                new Hyperlink("Set Seed")
        };
        for (int i = 0; i < 4; i++) {
            vert.setMargin(options[i], new Insets(0, 0, 0, 8));
            vert.getChildren().add(options[i]);
        }
        return vert;
    }

    public void setSize(Stage mainWindow, int w, int h) {
        mainWindow.setMaxHeight(h);
        mainWindow.setMaxWidth(w);
        mainWindow.setMinHeight(h);
        mainWindow.setMinWidth(w);
    }

    @Override
    public void handle(Event event) {
        //todo handlers
    }
}
