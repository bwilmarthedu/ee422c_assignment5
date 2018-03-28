package assignment5;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
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
    int WIDTH = 1000;
    int HEIGHT = 1000;
    Stage mainWindow;
    Button runTimeSteps;
    Button make;
    Button animate;
    Button seed;
    //todo scene for runStats

    public static void main(String[] args) {
        launch(args);


    }

    @Override
    public void start(Stage stage) throws Exception {
        mainWindow = stage;
        setSize(WIDTH, HEIGHT);
        mainWindow.setTitle("Critters Part Two, Electric Boogaloo");
        BorderPane border = new BorderPane();
        FlowPane buttons = new FlowPane();
        for (int i = 0; i < 4; i++) {
            buttons.getChildren().add(makeButtons()[i]);
        }
        buttons.setPadding(new Insets(5, 5, 5, 5));
        buttons.setVgap(10);
        buttons.setHgap(10);
//        border.setRight(buttons);
        Scene scene = new Scene(buttons, WIDTH, HEIGHT);
        mainWindow.setScene(scene);
        mainWindow.show();

        runTimeSteps.setOnAction((event)-> System.out.println("DEBUG: runTimeSteps"));
        make.setOnAction((event)-> System.out.println("DEBUG: make"));
        animate.setOnAction((event)-> System.out.println("DEBUG: animate"));
        seed.setOnAction((event)-> System.out.println("DEBUG: seed"));

    }

    public void setSize(int w, int h) {
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

    }

}
