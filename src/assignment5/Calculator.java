package assignment5;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Calculator extends Application {

    public Button b0, b1;

    @Override
    public void start(Stage primaryStage) {

        GridPane grid = new GridPane();

        b0 = new Button("0");
        b0.setId("0");
        b0.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        grid.add(b0, 0, 1);
        b0.setOnAction(myHandler);

        b1 = new Button("1");
        b1.setId("1");
        b1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        grid.add(b1, 0, 0);
        b1.setOnAction(myHandler);

        Scene scene = new Scene(grid, 365, 300);
//        scene.getStylesheets().add
//                (Calculator.class.getResource("calculator.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    final EventHandler<ActionEvent> myHandler = new EventHandler<ActionEvent>(){

        @Override
        public void handle(final ActionEvent event) {
            Button x = (Button) event.getSource();
            if (x.getId().equals(b0.getId()))
                System.out.println("0");
            else if(x.getId().equals(b1.getId()))
                System.out.println("1");
        }
    };
    public static void main(String[] args) {
        launch(args);
    }

}