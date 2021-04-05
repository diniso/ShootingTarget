package shooting;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;


public class Gun extends Group {

    private static final String gunUrl = "images\\gun.jpg";
    private final Rotate rotate;

    public Gun(double x , double y , double width , double height) throws MalformedURLException {

        Rectangle rec1 = new Rectangle(x , y , width , height);
        rec1.setStroke(Color.BLACK);
        rec1.setFill(new ImagePattern(new Image(new File(gunUrl).toURI().toURL().toString()) , 0 , 0 , 1 , 1 , true));

        rotate = new Rotate(0 , x + width/2 , y + height/2);

        this.getChildren().addAll(rec1);
        this.getTransforms().addAll(rotate);
    }

    public Rotate getMyRotate() {
        return rotate;
    }
}
