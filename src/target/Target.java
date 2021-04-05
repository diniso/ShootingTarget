package target;


import controlers.BulletCountControler;
import controlers.PointControler;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public abstract class  Target extends TargetWithoutNumbers {

    private int collectedPoints;
    private final int[] numbers;
    private final ArrayList<Text> brojevi = new ArrayList<>();
    private final BulletCountControler bulletcontroler;

    public Target(double x , double y , double r , int[] numbers, BulletCountControler bulletcontroler) {
        super(x,y,r,numbers.length);
        this.numbers = numbers;
        this.bulletcontroler = bulletcontroler;
        int numOfCircles = numbers.length;

        double circleWidth = r/numOfCircles;
        Font font = Font.font(8);

        Text txt = new Text(x + r/2 -circleWidth/2  , y + r/2,"" + numbers[0]);
        txt.setStroke(Color.BLACK);
        txt.setFont(font);
        this.getChildren().add(txt);
        brojevi.add(txt);

        for (int i = 1 ; i< numOfCircles ; i++) {
            txt = new Text(x + r/2 + i*circleWidth  , y + r/2,"" + numbers[i]);
            txt.setStroke(Color.RED);
            txt.setFont(font);
            this.getChildren().add(txt);
            brojevi.add(txt);
        }

    }

    protected void setListeners() {
        for (int i = 0 ; i < krugovi.length; i++) {
            krugovi[i].setOnMousePressed(new PointControler(this ,numbers[krugovi.length - 1 - i], bulletcontroler));
            brojevi.get(i).setOnMousePressed(new PointControler(this ,numbers[i], bulletcontroler));
        }
    }
    public abstract void stop(double x , double y);
    public abstract void play();
    public void setPoints(int points) {
        collectedPoints = points;
    }
    public int getPoints() {
        return collectedPoints;
    }
}
