package sample;

import controlers.GunControler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import levels.Level1;
import target.LinearMovingTarget;

import java.util.Random;

public class Main extends Application {

    public static final String cursorUrl = "images\\cursor.jpg";

    private final Random rd = new Random();
    private final double width = 700 , height = 500;

    private Scene getScene(double width, double height) {


        Group root = new Group();
        Level1 l1 = new Level1(width , height, 5, 2);

        root.getChildren().addAll(l1);
        return new Scene(root , width , height);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Target");
        primaryStage.setScene(getScene(width , height));
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
