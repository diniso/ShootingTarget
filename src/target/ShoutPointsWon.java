package target;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ShoutPointsWon extends Group {

    private FadeTransition fade;
    private ScaleTransition sc;


    public ShoutPointsWon(double x , double y, double expiringTime, int points) {
        double dx = 15, dy = 7;
        if (points < 100) dx -=3;

        Text poruka = new Text(x - dx, y + dy, ""+ points);
        fade = new FadeTransition(Duration.seconds(expiringTime), poruka);
        sc = new ScaleTransition(Duration.seconds(expiringTime), poruka);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        sc.setFromY(1.0);
        sc.setFromX(1.0);
        sc.setToX(0.5);
        sc.setToY(0.5);
        poruka.setFont(Font.font(20));
        this.getChildren().add(poruka);
    }

    public void play() {
        sc.play();
        fade.play();
    }
}
