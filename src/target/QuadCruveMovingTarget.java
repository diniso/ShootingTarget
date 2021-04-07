package target;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.QuadCurve;
import javafx.util.Duration;
import levels.Level;

public class QuadCruveMovingTarget extends Target{

    private final ScaleTransition scale;
    private final FadeTransition fade;
    private final PathTransition path;
    private final Level level;

    public QuadCruveMovingTarget(double x, double y, double r, int[] numbers , double x2 , double y2, double x3 , double y3, double seconds , Level l) {
        super(x, y, r, numbers);
        level = l;

        Duration t = Duration.seconds(seconds);

        scale = new ScaleTransition(t , this);
        scale.setFromX(1.0); scale.setFromY(1.0); scale.setToX(0.5); scale.setToY(0.5);

        fade = new FadeTransition(t , this);
        fade.setFromValue(1.0); fade.setToValue(0.0);

        QuadCurve curve = new QuadCurve(x  , y  , x2 , y2 , x3 , y3);

        path = new PathTransition(t , curve , this);


    }

    public void stop(double x , double y) {
        path.stop();
        fade.stop();
        scale.stop();
        level.insertPointsAndRemove(this , x , y);
    }

    public void play() {
        this.setListeners();
        scale.setOnFinished(actionEvent -> level.insertPointsAndRemove(this , 0 , 0));

        path.setInterpolator(Interpolator.EASE_IN);
        fade.setInterpolator(Interpolator.EASE_IN);
        scale.setInterpolator(Interpolator.EASE_IN);


        path.play();
        fade.play();
        scale.play();

    }
}
