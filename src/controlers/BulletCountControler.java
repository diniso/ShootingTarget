package controlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class BulletCountControler implements EventHandler<MouseEvent> {

    private final int numOfBulltets ;
    private int cnt = 0;
    private boolean canShoot = false;
    private final Text text;

    public BulletCountControler(int numOfBulltets, Text text) {
        this.text = text;
        this.numOfBulltets = numOfBulltets;
    }
    @Override
    public void handle(MouseEvent mouseEvent ) {
        bulletFired();
    }

    public boolean bulletFired() {
        if (!canShoot || cnt == numOfBulltets) return false;
        cnt++;
        text.setText("" + (numOfBulltets - cnt));
        return true;
    }

    public void setCanShoot() {
        canShoot = true;
    }

}
