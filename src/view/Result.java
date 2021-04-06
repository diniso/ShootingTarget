package view;


import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.Main;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Result extends Group {

    private static final Font textFont = Font.font(30);
    private static final double dx = 0.2 , dy = 0.12;

    public Result(double x , double y , double width , double height) {
        HashMap<Integer , Integer> score = Main.getScore();
        int col = Main.getScore().size() + 3;

        int finalscore = 0;
        for (int key : score.keySet()) {
            finalscore += key*score.get(key);
        }
        Text points = new Text(x + width/2 - dx*width, y + height/col - dy*height
                , "Points:\t" + finalscore);
        points.setFont(textFont);
        Text time = new Text(x + width/2 - dx*width, y + 2*height/col - dy*height
                ,"Time:\t" + Main.getTimeSpentInGame());
        time.setFont(textFont);

        Text hit = new Text(x + width/2 - dx*width/2, y + 3*height/col - dy*height/3
                , "Hits:");
        hit.setFont(textFont);

        Text hitOnTarget[] = new Text[score.size()];
        int index = 0;

        SortedMap<Integer , Integer> orderedmap = new TreeMap<Integer, Integer>(score);

        for (int key : orderedmap.keySet()) {
            hitOnTarget[index] = new Text(x + width/2 - dx*width, y + (4+index)*height/col
                    , "" + key + ":\t\t" + orderedmap.get(key));
            hitOnTarget[index++].setFont(textFont);
        }


        this.getChildren().addAll( points, time , hit);
        this.getChildren().addAll(hitOnTarget);
    }

}
