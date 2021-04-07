package controlers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.robot.Robot;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import levels.Level;
import sample.Main;

import java.util.List;

public class KeyControler implements EventHandler<KeyEvent> {

    private static final double dx = 15;
    private Node playable;
    private Group parent;
    private boolean levelEnded = true, endgame = false;
    private Level levelToPlay;
    private final Text foter;
    private Robot robot;


    public KeyControler(Level level, Node playable, Group parent, Text foter) {
        this.parent = parent;
        this.levelToPlay = level;
        this.foter = foter;
        this.playable = playable;
        robot = new Robot();
    }
    @Override
    public void handle(KeyEvent keyEvent) {

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
                double screenX = robot.getMouseX();
                double screenY = robot.getMouseY();
               double sceneX = Main.myStage.getX();
                double sceneY = Main.myStage.getY();
                Node node = pick(playable, screenX - sceneX ,screenY - sceneY);
                Event.fireEvent(node
                        ,new MouseEvent(MouseEvent.MOUSE_PRESSED, screenX - sceneX
                        , screenY - sceneY , screenX  , screenY ,
                        MouseButton.PRIMARY , 1 , true, true, true, true, true,
                        true, true, true, true, true, null)
                );
                break;

        }
    }

    public void setEndGame() {
        endgame = true;
        foter.setText("Press ENTER to play again!");
        foter.getTransforms().addAll(new Translate(-Main.width / 20 , 0));
    }

    private static Node pick(Node node, double sceneX, double sceneY) {
        Point2D p = node.sceneToLocal(sceneX, sceneY, true /* rootScene */);

        // check if the given node has the point inside it, or else we drop out
        if (!node.contains(p)) return null;

        // at this point we know that _at least_ the given node is a valid
        // answer to the given point, so we will return that if we don't find
        // a better child option
        if (node instanceof Parent) {
            // we iterate through all children in reverse order, and stop when we find a match.
            // We do this as we know the elements at the end of the list have a higher
            // z-order, and are therefore the better match, compared to children that
            // might also intersect (but that would be underneath the element).
            Node bestMatchingChild = null;
            List<Node> children = ((Parent)node).getChildrenUnmodifiable();
            for (int i = children.size() - 1; i >= 0; i--) {
                Node child = children.get(i);
                p = child.sceneToLocal(sceneX, sceneY, true /* rootScene */);
                if (child.isVisible() && !child.isMouseTransparent() && child.contains(p)) {
                    bestMatchingChild = child;
                    break;
                }
            }

            if (bestMatchingChild != null) {
                return pick(bestMatchingChild, sceneX, sceneY);
            }
        }

        return node;
    }
}
