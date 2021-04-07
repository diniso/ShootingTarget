package shooting;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Translate;

public class Gunpoint extends Group {

    private final Translate translate;
    private static final Color GunpointColor = Color.GREEN;

    public Gunpoint(double x , double y , double width , double height) {
        Line l1 = new Line(x , y , x + width , y + height);
        Line l2 = new Line(x , y + height, x + width , y );

        l1.setStrokeWidth(2);
        l2.setStrokeWidth(2);

        l1.setStroke(GunpointColor);
        l2.setStroke(GunpointColor);


        this.getChildren().addAll(l1 , l2);
        this.getTransforms().add(translate = new Translate(0 , 0));

    }

    public Translate getMyTranslate() {
        return translate;
    }
}
