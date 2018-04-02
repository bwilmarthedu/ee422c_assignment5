package assignment5;

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

import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

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
        AnchorPane critterWorld = new AnchorPane();
        TextArea stats = new TextArea();

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
        critterOptions.getItems().addAll("Craig", "Algae", "Algaephobic", "Critter1", "Critter2", "Critter3", "Critter4");
        critterOptions.setEditable(false);
        String value = (String) critterOptions.getValue();
        TextField numOf = new TextField("Amount");

        fp.getChildren().addAll(t, critterOptions, numOf);
        makeTab.setContent(fp);
        return makeTab;
    }

    private Tab buildRunTab(Tab runTab) {
        return runTab;
    }

    private Tab buildAnimateTab(Tab animateTab) {
        return animateTab;
    }

    private Tab buildSeedTab(Tab seedTab) {
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

    public Button[] makeButtons() {
        runTimeSteps = new Button("Run");
        runTimeSteps.setLayoutY(100);
        make = new Button("Make Critters");
        make.setLayoutY(200);
        animate = new Button("Animate World");
        animate.setLayoutY(300);
        seed = new Button("Set Seed");
        seed.setLayoutY(400);
        Button[] array = {runTimeSteps, make, animate, seed};
        return array;
    }


    public void makeEventHandlers() {
        runTimeSteps.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try{
                    Integer numSteps = new Integer(1);  // TODO Get numSteps from slider or textbox
                    for(int i = 0; i < numSteps; i++) {
                        Critter.worldTimeStep();
                    }
                }
                catch(Exception e){
                    // Print error in textbox
                }
            }
        });
        make.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Critter.makeCritter(); // TODO Get input from dropdown textbox
            }
        });
        animate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Black out all unnecessary inputs
                Timer timer = new Timer();
                TimerTask doStep = new TimerTask() {
                    @Override
                    public void run() {
                        Integer numSteps = new Integer(1);  // Get numSteps from slider or textbox
                        for(int i = 0; i < numSteps; i++) {
                            Critter.worldTimeStep();
                        }
                    }
                };
                timer.scheduleAtFixedRate(doStep, 100, 1000);
            }
        });
        seed.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    //Critter.setSeed(Integer.parseInt()); // TODO Take input from textbox
                }
                catch(Exception e){
                    // Print error in textbox
                }
            }
        });
    }

    @Override
    public void handle(Event event) {
        runTimeSteps.setOnAction((ButtonEvent) -> System.out.println("DEBUG: runTimeSteps"));
//        make.setOnAction((event)-> System.out.println("DEBUG: make"));
//        animate.setOnAction((event)-> System.out.println("DEBUG: animate"));
//        seed.setOnAction((event)-> System.out.println("DEBUG: seed"));
    }

    private void print(Node node) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            System.out.println(job.jobStatusProperty().asString());
            System.out.println("cool");
            boolean printed = job.printPage(node);
            if (printed) {
                job.endJob();
            } else {
                System.out.println("Printing failed.");
            }
        } else {
            System.out.println("Could not create a printer job.");
        }
    }
}
