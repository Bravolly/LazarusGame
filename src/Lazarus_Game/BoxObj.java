package Lazarus_Game;

import Platform.GameObj;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Bravolly Pich.
 */
public class BoxObj extends GameObj implements Observer {
    char type; //0: wall, 1: card, 2: wood, 3: stone, 4: metal
    int tempX, tempY;
    boolean falling;

    public BoxObj(BufferedImage[] sprite) {
        super(sprite);
    }

    public BoxObj(BufferedImage[] sprite, int x, int y, int speed, char type, boolean fall) {
        super(sprite);
        setXY(x,y);
        tempX = x;
        tempY = y;
        this.speed = speed;
        this.type = type;
        this.falling = fall;
    }

    /**
     * Actions to be performed depending on the number from collisionType
     */
    @Override
    public void collisionAction() {
        switch (collisionType) {
            case '0':
                falling = false;
                setXY(tempX,tempY);
                break;
            case '1':
                visible = false;
                break;
            default:
                break;
        }
    }

    public void fall() {
        tempY = y;
        setY(y + speed);
    }

    public boolean setFalling(boolean b) {
        return falling = b;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setType(char t) {
        type = t;
    }

    public char getType() {
        return type;
    }

    public void update() {
        collisionAction();
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void draw(Graphics g, ImageObserver obs) {
        if (visible) {
            g.drawImage(sprite[frame], x, y, obs);

            if (falling) {
                fall();
            }
        }
    }
}