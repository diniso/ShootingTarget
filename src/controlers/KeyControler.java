package controlers;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.robot.Robot;

public class KeyControler implements EventHandler<KeyEvent> {

    private static final double dx = 15;
    private Node playable;


    public KeyControler(Node playable) {
        this.playable = playable;
    }
    @Override
    public void handle(KeyEvent keyEvent) {
        Robot robot = new Robot();
        switch (keyEvent.getCode()) {
            case W:
                robot.mouseMove(robot.getMouseX()  , robot.getMouseY() - dx);

                break;

            case S:
                robot.mouseMove(robot.getMouseX()  , robot.getMouseY() + dx);
                break;

            case A:
                robot.mouseMove(robot.getMouseX()  -dx , robot.getMouseY() );
                break;

            case D:
                robot.mouseMove(robot.getMouseX()  +dx, robot.getMouseY() );
                break;

            case SPACE:
                robot.mousePress(MouseButton.PRIMARY);
            //    playable.setCursor(Cursor.NONE);

        }
    }

}
