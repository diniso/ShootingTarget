package controlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import target.Target;

public class PointControler implements EventHandler<MouseEvent> {

    private final Target target;
    protected final int points;
    private final BulletCountControler bulletcontroler;

    public PointControler( Target target , int points, BulletCountControler bulletcontroler) {
        this.target = target;
        this.points = points;
        this.bulletcontroler = bulletcontroler;
    }

    @Override
    public void handle(MouseEvent mouseEvent ) {
            if (!bulletcontroler.bulletFired()) return;
            target.setPoints(points);
            target.stop(mouseEvent.getSceneX() , mouseEvent.getSceneY());

    }
}
