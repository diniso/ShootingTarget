package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import levels.Level;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class Main extends Application {

    private static final int levelNumber = 3;
    private static final int levelRepetition= 2;
    private static final Random rd = new Random();
    public static final double width = 800 , height = 600;
    private static int[] targetNumbers;
    private static final int centerPoints = 150 , minNumOfCircles = 6, maxNumOfCircles = 7;
    private static final int hoursInSeconds = 3600;
    private static final int minutesInSeconds = 60;
    private static final double minimumPerctangeBullets = 1.0;

    private static final ArrayList<Level> leveli = new ArrayList<>();
    private static final Group root = new Group();
    private static Date startTimeOfGame;
    private static long milisecondsPlayed;
    private static double PerctangeBullets = 2.0;

    private Scene getScene( double width, double height){
        Scene scene = null;
        try {
            createLevelsAndStart();
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


    public static void createLevelsAndStart() throws Exception {
        leveli.clear();
        milisecondsPlayed = 0;
        double velocity = 1.0;
        for (int j = 0 ; j < levelRepetition ; j++) {
            for (int i = 0 ; i < levelNumber ; i++) {
                Level l = new Level(width , height, PerctangeBullets, velocity, i+1, j+1, root);
                leveli.add(l);
            }
            velocity += 0.25;
            PerctangeBullets = minimumPerctangeBullets + (PerctangeBullets - minimumPerctangeBullets)/2;
        }

        for (int i = leveli.size() -1 ; i > 0 ; i--)
            leveli.get(i-1).setNextLevel(leveli.get(i));

        root.getChildren().addAll(leveli.get(0));
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

    public static void startTime() {
        startTimeOfGame = new Date();
    }

    public static void finishLevel() {
        Date endTimeOfLevel = new Date();
        milisecondsPlayed += endTimeOfLevel.getTime() - startTimeOfGame.getTime() ;
    }

    public static long getMilisecondsPlayed() {
        return milisecondsPlayed;
    }

    public static  String getTimeSpentInGame() {
        int secondsPlayed = (int)(milisecondsPlayed / 1000);
        int hours = secondsPlayed / hoursInSeconds , minutes = secondsPlayed / minutesInSeconds , seconds = secondsPlayed%minutesInSeconds;
        String time = "";
        if (hours < 10) time += "0" + hours + ":";
        else time += hours + ":";
        if (minutes < 10) time += "0" + minutes  + ":";
        else time += minutes + ":";
        if (seconds < 10) time += "0" + seconds ;
        else time += seconds;

        return time;
    }
}
