package assignment5;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;





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

    public static void main(String[] args) {
        launch(args);


    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Critters Part Two");

        BorderPane border = new BorderPane();
        AnchorPane critterWorld = new AnchorPane();
        AnchorPane statsBox = new AnchorPane();
        TabPane tabPane = new TabPane();
        setSize(stage, WIDTH, HEIGHT);

        Group root = new Group();
        Scene scene = new Scene(root, 400, 250);

        tabPane = setTabs(tabPane);
        Critter.displayWorld(critterWorld);

        border.setRight(tabPane);
        border.setCenter(critterWorld);
        border.setBottom(statsBox);
        // bind to take available space
        root.getChildren().add(border);
        stage.setScene(scene);
        stage.show();
    }

    private TabPane setTabs( TabPane tabPane){

        String[] tabnames = new String[]{ "Make", "Run", "Animate", "Seed"};
        for (int i = 0; i < 4; i++) {
            Tab tab = new Tab();
            tab.setText(tabnames[i]);
            Label tabName = new Label(tabnames[i]);
            HBox hbox = new HBox();
            hbox.getChildren().add(tabName);
            hbox.setAlignment(Pos.CENTER);
            tab.setContent(hbox);
            tabPane.getTabs().add(tab);
        } return tabPane;
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

    @Override
    public void handle(Event event) {
        runTimeSteps.setOnAction((ButtonEvent) -> System.out.println("DEBUG: runTimeSteps"));
//        make.setOnAction((event)-> System.out.println("DEBUG: make"));
//        animate.setOnAction((event)-> System.out.println("DEBUG: animate"));
//        seed.setOnAction((event)-> System.out.println("DEBUG: seed"));
    }

}
