package shooting;


import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.net.MalformedURLException;

public class Bullet extends Group {

    private static final String bulletURL = "images\\bullet.jpg";


    public Bullet(double x , double y , double width , double height) throws MalformedURLException {

        Rectangle rec1 = new Rectangle(x , y , width , height);
        rec1.setStroke(Color.BLACK);
        rec1.setFill(new ImagePattern(new Image(new File(bulletURL).toURI().toURL().toString()) , 0 , 0 , 1 , 1 , true));

        this.getChildren().addAll(rec1);

    }


}
