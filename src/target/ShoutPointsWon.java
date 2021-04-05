package target;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ShoutPointsWon extends Group {

    private final FadeTransition fade;
    private final ScaleTransition scale;


    public ShoutPointsWon(double x , double y, double expiringTime, int points) {
        double dx = 15, dy = 7;
        if (points < 100) dx -=3;

        Text poruka = new Text(x - dx, y + dy, ""+ points);
        fade = new FadeTransition(Duration.seconds(expiringTime), poruka);
        scale = new ScaleTransition(Duration.seconds(expiringTime), poruka);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        scale.setFromY(1.0);
        scale.setFromX(1.0);
        scale.setToX(0.5);
        scale.setToY(0.5);
        poruka.setFont(Font.font(20));
        this.getChildren().add(poruka);
    }

    public void play() {
        scale.play();
        fade.play();
    }
}
