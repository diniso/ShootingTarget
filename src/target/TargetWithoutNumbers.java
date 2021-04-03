package target;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TargetWithoutNumbers extends Group {

    protected Circle[] krugovi;

    private Color getRainbowColor() {
        return Color.RED;
    }

    public TargetWithoutNumbers(double x , double y , double r, int numOfCircles) {
        Circle[] circles = new Circle[numOfCircles];
        for (int i = 0 ; i < numOfCircles ; i++) {
            circles[i] = new Circle(x + r/2 , y + r/2 , r / numOfCircles* (numOfCircles-i));

            if (i % 2 == 0) {
                circles[i].setFill(Color.BLACK);
                continue;
            }

            circles[i].setFill(Color.WHITE);
        }

        circles[numOfCircles-1].setFill(getRainbowColor());

        krugovi = circles;
        this.getChildren().addAll(circles);
    }
}
