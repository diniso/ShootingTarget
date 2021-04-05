package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import levels.Level;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Main extends Application {

    private static final Random rd = new Random();
    private static final double width = 800 , height = 600;
    private static int[] targetNumbers;
    private static final int centerPoints = 150 , minNumOfCircles = 6, maxNumOfCircles = 7;

    private static final ArrayList<Level> leveli = new ArrayList<>();

    private Scene getScene( double width, double height){
        Scene scene = null;
        try {
            Group root = new Group();
            Level l1 = new Level( width , height, 5 , 1 , 1, 1, root );
            Level l2 = new Level( width , height, 5 , 1.5 , 1, 2, root);
            l1.setNextLevel(l2);
            leveli.add(l1);
            leveli.add(l2);
            l1.start();

            root.getChildren().addAll(l1);
            scene = new Scene(root , width , height);
        }
        catch (Exception e) {e.printStackTrace();}

        return scene;
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Target");
        primaryStage.setScene(getScene( width , height));
        primaryStage.setResizable(false);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }

    public static int[] getTargetNumbers() {
        if (targetNumbers == null) {
            int num = rd.nextInt(maxNumOfCircles - minNumOfCircles + 1) + minNumOfCircles;
            targetNumbers = new int[num];
            targetNumbers[0] = centerPoints;
            double dx = centerPoints *1.0/ num /10;
            for (int i = 1 ; i < num ; i++)
                targetNumbers[i] = targetNumbers[0] - ((int)Math.round(dx*i)*10);
        }

        return targetNumbers;
    }

    public static HashMap<Integer , Integer> getScore() {
        HashMap<Integer , Integer> ret = new HashMap<>();
        for (Level l : leveli) {
            HashMap<Integer , Integer> levelpoints = l.getLevelScore();
            for (int key : levelpoints.keySet()) {
                if (ret.containsKey(key))  ret.put(key , ret.get(key) + levelpoints.get(key));
                else ret.put(key , levelpoints.get(key));
            }
        }

        return ret;
    }

}
