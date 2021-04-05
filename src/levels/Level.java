package levels;

import controlers.BulletCountControler;
import controlers.GunControler;
import controlers.KeyControler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sample.Main;
import shooting.Bullet;
import shooting.Gun;
import shooting.Gunpoint;
import target.ElipticMovingTarget;
import target.ShoutPointsWon;
import target.Target;
import target.TargetWithoutNumbers;
import view.Wall;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Level extends Group{

    private static final String XmlEllipticObjectName = "elliptic";
    private static final String XmlLinearObjectName = "linear";
    private static final String XmlQuadCurveObjectName = "quadcurve";
    private static final String XmlObjectName = "object";
    private static final String XmlTypeName = "type";
    private static final String levelURL = "configuration\\level";
    private static final String imageURL = "images\\background";

    private static final double r = 70.0 , gunWidth = 50 , gunHeight = 75 , gunpointWidth = 15 , gunpointHeight = 15;
    private static final double diffPer = 0.2 ,expiringTime = 2;
    private static final Font fontsize = Font.font(30);

    private final Text numOfTargetsLeft, numOfPointsWon;
    private Level nextLevel;

    private final double width , height;
    private final int numOfTargetsOnScene;
    private final Group parent;

    private int index = 0;
    private int points = 0;
    private final HashMap<Integer , Integer>  numOfPointsHit = new HashMap<>();
    private ArrayList<Target> targets;


    public Level(double width , double height, int numOfBullets, double velocity , int levelNumber, int numOfTragetsOnScene, Group parent)throws Exception {
        this.height = height;
        this.width = width;
        this.numOfTargetsOnScene = numOfTragetsOnScene;
        this.parent = parent;
        BulletCountControler bulletcontroler;
        Text numOfBulletsLeft = new Text("" + numOfBullets);


        Group mainStage = new Group();
        Group playable = new Group();

        Rectangle background = new Rectangle(0 , 0 , width*(1.0 - diffPer) , height*(1.0 - diffPer)-gunHeight);
        background.setFill(Color.TRANSPARENT);
        background.setStroke(Color.BLACK);

        Wall wall = new Wall(0  , height*(1.0 - diffPer) - gunHeight  , width*(1.0 - diffPer) , gunHeight);

        Gun gun = new Gun((width*(1.0 - diffPer) - gunWidth)/2 ,height*(1.0 - diffPer) - gunHeight, gunWidth , gunHeight );
        Gunpoint point = new Gunpoint(-gunpointWidth /2 , -gunpointHeight / 2 , gunpointWidth, gunpointHeight);

        playable.setOnMouseMoved( new GunControler(gun.getMyRotate(), point.getMyTranslate()));
        playable.setOnMouseClicked(bulletcontroler = new BulletCountControler(numOfBullets, numOfBulletsLeft));


        createTargets(levelNumber , velocity, bulletcontroler);
        playable.getChildren().addAll(background, gun);
        playable.getChildren().addAll(point);
        playable.getChildren().addAll(targets);


        mainStage.getChildren().addAll(wall , playable);

        mainStage.getTransforms().addAll(new Translate(diffPer/2*width, diffPer/2*height));
        playable.setCursor(Cursor.NONE);

        Group border = new Group();

        Rectangle pozadina = new Rectangle(0 , 0 , width , height);
        pozadina.setFill(new ImagePattern(new Image(new File(imageURL + levelNumber + ".jpg").toURI().toURL().toString()), 0 , 0 , 1 , 1 , true));


        double upperWindowWidth = width*(1.0-diffPer)*(1.0-diffPer) ,
                upperWindowHeight = height*diffPer/2*(1.0-diffPer);
        double upperWindowX =  width*diffPer/2  + upperWindowWidth*diffPer/2,
                upperWindowY =  upperWindowHeight*diffPer/2;


        Group bulletgroup = new Group();
        Bullet bullet = new Bullet(upperWindowX , upperWindowY*2, upperWindowWidth *diffPer/2, upperWindowHeight*(1.0-diffPer*3/4));
        numOfBulletsLeft.setFont(fontsize);
        numOfBulletsLeft.setX(upperWindowX + upperWindowWidth *diffPer*3/4);
        numOfBulletsLeft.setY(upperWindowY + upperWindowHeight *(1.0-diffPer));
        bulletgroup.getChildren().addAll(bullet , numOfBulletsLeft);


        numOfPointsWon = new Text("Points: " + points);
        numOfPointsWon.setFont(fontsize);
        numOfPointsWon.setX(upperWindowX + upperWindowWidth/3);
        numOfPointsWon.setY(upperWindowY + upperWindowHeight *(1.0-diffPer));

        Group targetgroup = new Group();
        numOfTargetsLeft = new Text("" + targets.size());
        TargetWithoutNumbers targetpicture = new TargetWithoutNumbers(upperWindowX + upperWindowWidth*7/8, upperWindowY * 3.5, (upperWindowWidth * diffPer / 2)/2, Main.getTargetNumbers().length) ;
        numOfTargetsLeft.setFont(fontsize);
        numOfTargetsLeft.setX(upperWindowX + upperWindowWidth*7/8 + upperWindowWidth *diffPer*1/2);
        numOfTargetsLeft.setY(upperWindowY + upperWindowHeight *(1.0-diffPer));
        targetgroup.getChildren().addAll(targetpicture , numOfTargetsLeft);


        border.getChildren().addAll(pozadina, bulletgroup, numOfPointsWon, targetgroup);

        this.getChildren().addAll(border, mainStage);

        background.setOnKeyPressed(new KeyControler(playable));
        background.setFocusTraversable(true);

    }

    private List<Target> createTargets(int levelNumber , double velocity, BulletCountControler bulletcontroler) throws Exception {
        targets = new ArrayList<>();

        int[] numbers = Main.getTargetNumbers();
        for (int num : numbers)numOfPointsHit.put(num , 0);

        File file = new File(levelURL + levelNumber + ".xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        NodeList nodelist = doc.getElementsByTagName(XmlObjectName);


        for (int i = 0 ; i < nodelist.getLength(); i++) {
            Element node = (Element)nodelist.item(i);
            if (XmlEllipticObjectName.equals(node.getAttributes().getNamedItem(XmlTypeName).getTextContent())) {
                double x = Double.parseDouble(node.getElementsByTagName("x").item(0).getTextContent());
                double y = Double.parseDouble(node.getElementsByTagName("y").item(0).getTextContent());
                double radiusX = Double.parseDouble(node.getElementsByTagName("radiusX").item(0).getTextContent());
                double radiusY = Double.parseDouble(node.getElementsByTagName("radiusY").item(0).getTextContent());
                double experiTime = Double.parseDouble(node.getElementsByTagName("expireTime").item(0).getTextContent());
                boolean direction = Boolean.parseBoolean(node.getElementsByTagName("rightSideStart").item(0).getTextContent());
                ElipticMovingTarget tar = new ElipticMovingTarget(x , y , r , numbers,
                        radiusX , radiusY , experiTime / velocity , direction , this, bulletcontroler);
                targets.add(tar);
                System.out.println(direction);
                continue;
            }
        }


   /*     ElipticMovingTarget pom = new ElipticMovingTarget(150 , 100 , r , numbers,
                80 , 50 , 6.0 / velocity , true , this, bulletcontroler);

        ElipticMovingTarget pom2 = new ElipticMovingTarget(300 , 100 , r , numbers
                , 80 , 50 , 6.0 / velocity, false, this, bulletcontroler);

        ElipticMovingTarget pom3 = new ElipticMovingTarget(450 , 100 , r , numbers
                , 80 , 50 , 6.0 / velocity, false, this, bulletcontroler);

        targets.add(pom);
        targets.add(pom2);
        targets.add(pom3);
        */
        return targets;
    }

    public void start() {
        for (int i = 0 ; i < numOfTargetsOnScene ; i++)
            targets.get(i).play();
        index = numOfTargetsOnScene;

    }

    public void insertPointsAndRemove(Target t, double x , double y) {

        ((Group) ((Group)this.getChildren().get(1)).getChildren().get(1)).getChildren().remove(t);
        numOfTargetsLeft.setText("" + (targets.size() - (index - numOfTargetsOnScene + 1)));

        if (t.getPoints() > 0) {
            points += t.getPoints();
            numOfPointsHit.put(t.getPoints() , numOfPointsHit.get(t.getPoints()) + 1);
            numOfPointsWon.setText("Points: " + points);
            ShoutPointsWon poruka = new ShoutPointsWon(x - width*diffPer/2, y - height*diffPer/2, expiringTime, t.getPoints());
            ((Group) ((Group)this.getChildren().get(1)).getChildren().get(1)).getChildren().add(poruka);
            poruka.play();
        }

        if (index >= targets.size()) {

            if (++index == targets.size() + numOfTargetsOnScene) {
                System.out.println("Gotov nivo");
                System.out.println(numOfPointsHit);

                if (nextLevel != null) {
                    parent.getChildren().remove(this);
                    parent.getChildren().addAll(nextLevel);
                    nextLevel.start();
                }
                else {
                    System.out.println("Ovde treba da se zavrsi igra");
                    System.out.println(Main.getScore());
                }
            }
        }
        else {
            targets.get(index).play();
            index++;
        }


    }

    public void setNextLevel(Level l) {
        this.nextLevel = l;
    }

    public HashMap<Integer , Integer> getLevelScore() {
        return numOfPointsHit;
    }
}
