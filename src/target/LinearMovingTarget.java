package target;

import controlers.BulletCountControler;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import levels.Level;

public class LinearMovingTarget extends Target{

    private ScaleTransition scale;
    private FadeTransition fade;
    private TranslateTransition translate;
    private Level level;


    public LinearMovingTarget(double x, double y, double r, int numOfCircles, int[] numbers , double width , double height, double seconds, Level l, BulletCountControler bulletcontroler) {
        super(x, y, r, numOfCircles, numbers, bulletcontroler);
        this.setOpacity(0.0);
        level = l;

        Duration t = Duration.seconds(seconds);

        scale = new ScaleTransition(t , this);
        scale.setFromX(1.0); scale.setFromY(1.0); scale.setToX(0.5); scale.setToY(0.5);

        fade = new FadeTransition(t , this);
        fade.setFromValue(1.0); fade.setToValue(0.0);

        translate = new TranslateTransition(t , this);
        translate.setByX(width);
        translate.setByY(height);

    }

    public void stop(double x , double y) {
        translate.stop();
        fade.stop();
        scale.stop();
        level.insertPointsAndRemove(this , x , y);

    }

    public void play() {
        this.setListeners();
        scale.setOnFinished(actionEvent -> {
            level.insertPointsAndRemove(this , 0 , 0);
        });
        translate.play();
        fade.play();
        scale.play();
        this.setOpacity(1);
    }
}
