package controlers;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.robot.Robot;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import levels.Level;
import sample.Main;

public class KeyControler implements EventHandler<KeyEvent> {

    private static final double dx = 15;
    private Node playable;
    private Group parent;
    private boolean levelEnded = true, endgame = false;
    private Level levelToPlay;
    private final Text foter;


    public KeyControler(Level level, Node playable, Group parent, Text foter) {
        this.parent = parent;
        this.levelToPlay = level;
        this.foter = foter;
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

            case ENTER:
                    if (endgame) {
                        try {
                            parent.getChildren().remove(levelToPlay);
                            Main.createLevelsAndStart();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    if (levelEnded) {
                        foter.setText("");
                        Main.startTime();
                        levelToPlay.start();
                        levelEnded = false;
                    }

                break;

            case SPACE:
                robot.mousePress(MouseButton.PRIMARY);
            //    playable.setCursor(Cursor.NONE);

        }
    }

    public void setEndGame() {
        endgame = true;
        foter.setText("Press ENTER to play again!");
        foter.getTransforms().addAll(new Translate(-Main.width / 20 , 0));
    }
}
