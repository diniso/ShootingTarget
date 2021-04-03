package controlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class BulletCountControler implements EventHandler<MouseEvent> {

    private int numOfBulltets , cnt = 0;
    public BulletCountControler(int numOfBulltets) {
        this.numOfBulltets = numOfBulltets;
    }
    @Override
    public void handle(MouseEvent mouseEvent) {
        bulletFired();
    }

    public boolean bulletFired() {
        if (cnt == numOfBulltets) return false;
        cnt++;
        return true;
    }

}
