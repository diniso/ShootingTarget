package controlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class GunControler implements EventHandler<MouseEvent> {

    private Rotate rotate;
    private Translate translate;

    public GunControler(Rotate rotate, Translate translate) {
        this.translate = translate;
        this.rotate = rotate;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();

        double angle = Math.toDegrees(Math.atan((rotate.getPivotY()-y) / (rotate.getPivotX()-x))) * -1;
        if (angle > 0.0) rotate.setAngle(90.0 - angle);
        else rotate.setAngle(270.0 -  angle);

        translate.setX(x);
        translate.setY(y);

        mouseEvent.consume();

    }
}
