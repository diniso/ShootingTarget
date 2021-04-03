package target;


import controlers.BulletCountControler;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;
import levels.Level;

public class ElipticMovingTarget extends Target{

    private ScaleTransition scale;
    private FadeTransition fade;
    private PathTransition path;
    private Level level;

    public ElipticMovingTarget(double x, double y, double r, int numOfCircles, int[] numbers , double radiusX , double radiusY, double seconds , boolean direction , Level l, BulletCountControler bulletcontroler) {
        super(x, y, r, numOfCircles, numbers, bulletcontroler);
        this.setOpacity(0);
        level = l;

        Duration t = Duration.seconds(seconds);

        scale = new ScaleTransition(t , this);
        scale.setFromX(1.0); scale.setFromY(1.0); scale.setToX(0.5); scale.setToY(0.5);

        fade = new FadeTransition(t , this);
        fade.setFromValue(1.0); fade.setToValue(0.0);

        Ellipse el;
        if (direction) {
            el = new Ellipse(x + r/2+ radiusX, y + r/2, radiusX,radiusY);
        }
        else {
            el = new Ellipse(x +r/2 - radiusX ,y + r/2, radiusX,radiusY);
        }

        el.setFill(null);
        el.setStroke(Color.BLUE);

        path = new PathTransition(t , el , this);

    }

    public void stop(double x , double y) {
        path.stop();
        fade.stop();
        scale.stop();
        level.insertPointsAndRemove(this , x , y);
    }

    public void play() {
        this.setListeners();
        scale.setOnFinished(actionEvent -> {
            level.insertPointsAndRemove(this , 0 , 0);
        });
        path.play();
        fade.play();
        scale.play();
        this.setOpacity(1);
    }
}
