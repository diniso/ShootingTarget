package view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;


public class Wall extends Group {

    //private static final String wallURL = "images/wall.jpg";
    private static final double brickHeight = 15 , brickWidth = 75, brickStroke = 1.5;
    private static final Color bojaLinje = Color.DARKGRAY , pozadinskaBoja = Color.rgb(104 , 45 , 12);


    public Wall(double x, double y, double width, double height) {
        Rectangle rec = new Rectangle(x , y , width , height);
     //   rec.setFill(new ImagePattern(new Image(wallURL) , 0 , 0 , width*3 , height*7 , false));
        rec.setFill(pozadinskaBoja);
        this.getChildren().addAll(rec);


        for (int i = 0 ; i < height/brickHeight; i++) {
            Line l = new Line(x , y + (i+1)*brickHeight, x + width , y + (i+1)*brickHeight);
            l.setFill(bojaLinje);

            double offset = 0;
            if (i % 2 == 1) offset = brickWidth/2;

            for (int j = 0 ; j < width / brickWidth -1; j++) {
                Line l2 = new Line(x + (j + 1)*brickWidth - offset, y + (i)*brickHeight, x + (j + 1)*brickWidth -offset, y + (i+1)*brickHeight);
                l2.setFill(bojaLinje);
                l2.setStrokeWidth(brickStroke);
                this.getChildren().addAll(l2);
            }
            if (i != height/brickHeight - 1) this.getChildren().addAll(l);
        }




    }


}
