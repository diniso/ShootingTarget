package levels;

import controlers.BulletCountControler;
import controlers.GunControler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import shooting.Gun;
import shooting.Gunpoint;
import target.ElipticMovingTarget;
import target.ShoutPointsWon;
import target.Target;

import java.util.ArrayList;
import java.util.List;

public class Level1 extends Group implements Level{

    private final int maxObjectsOnStage = 5 ;
    private final double r = 70.0 , gunWidth = 50 , gunHeight = 70 , gunpointWidth = 15 , gunpointHeight = 15;
    private final double diffPer = 0.2;

    private int points = 0;
    private final BulletCountControler bulletcontroler ;
    private final double expiringTime;
    private double width , height;


    public Level1(double width , double height, int numOfBullets, double expireTime ) {
        expiringTime = expireTime;
        this.height = height;
        this.width = width;

        Group mainStage = new Group();
        Group playable = new Group();

        Rectangle background = new Rectangle(0 , 0 , width*(1.0 - diffPer) , height*(1.0 - diffPer)-gunHeight);
        background.setFill(Color.LIGHTGRAY);

        Rectangle wall = new Rectangle(0  , height*(1.0 - diffPer) - gunHeight  , width*(1.0 - diffPer) , gunHeight);
        wall.setFill(Color.GRAY);

        Gun gun = new Gun((width*(1.0 - diffPer) - gunWidth)/2 ,height*(1.0 - diffPer) - gunHeight, gunWidth , gunHeight );
        Gunpoint point = new Gunpoint(-gunpointWidth /2 , -gunpointHeight / 2 , gunpointWidth, gunpointHeight);

        playable.setOnMouseMoved( new GunControler(gun.getMyRotate(), point.getMyTranslate()));
        playable.setOnMouseClicked(bulletcontroler = new BulletCountControler(numOfBullets));

        List<Target> lista = getTargets(bulletcontroler);

        playable.getChildren().addAll(background, gun);
        playable.getChildren().addAll(point);
        playable.getChildren().addAll(lista);


        mainStage.getChildren().addAll(wall , playable);

        mainStage.getTransforms().addAll(new Translate(diffPer/2*width, diffPer/2*height));
        playable.setCursor(Cursor.NONE);


        Rectangle pozadina = new Rectangle(0 , 0 , width , height);
        pozadina.setFill(Color.ORANGE);

        this.getChildren().addAll(pozadina, mainStage);

        this.start(lista);


    }

    private List<Target> getTargets(BulletCountControler bulletcontroler) {
        ArrayList lista = new ArrayList<>();

        ElipticMovingTarget pom = new ElipticMovingTarget(150 , 100 , 70 , 6 , new int[] {
                150 , 120 , 100 , 70 , 50 , 30
        }, 80 , 50 , 8 , false , this, bulletcontroler);

        ElipticMovingTarget pom2 = new ElipticMovingTarget(300 , 100 , 70 , 6 , new int[] {
                150 , 120 , 100 , 70 , 50 , 30
        }, 80 , 50 , 8 , false, this, bulletcontroler);

        ElipticMovingTarget pom3 = new ElipticMovingTarget(450 , 100 , 70 , 6 , new int[] {
                150 , 120 , 100 , 70 , 50 , 30
        }, 80 , 50 , 8 , false, this, bulletcontroler);

        lista.add(pom);
        lista.add(pom2);
        lista.add(pom3);

        return lista;
    }

    private void start(List<Target> targets) {
        for (Target t : targets)
            t.play();
    }

    public void insertPointsAndRemove(Target t, double x , double y) {
        points += t.getPoints();
        ((Group) ((Group)this.getChildren().get(1)).getChildren().get(1)).getChildren().remove(t);
        System.out.println(points);

        if (t.getPoints() > 0) {
            ShoutPointsWon poruka = new ShoutPointsWon(x - width*diffPer/2, y- height*diffPer/2 , expiringTime, t.getPoints());
            ((Group) ((Group)this.getChildren().get(1)).getChildren().get(1)).getChildren().add(poruka);
            poruka.play();
        }

    }


}
