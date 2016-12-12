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
    char type; //0: wall, 1: card, 2: metal, 3: stone, 4: wood
    boolean falling = true;

    public BoxObj(BufferedImage[] sprite) {
        super(sprite);
    }

    public BoxObj(BufferedImage[] sprite, int x, int y, int speed, char type) {
        super(sprite);
        setX(x);
        setY(y);
        this.speed = speed;
        this.type = type;
    }

    /**
     * Actions to be performed depending on the number from collisionType
     */
    @Override
    public void collisionAction() {
        switch (collisionType) {
            case '0':
                falling = false;
                break;
            case '1':
                visible = false;
                break;
            default:
                break;
        }
    }

    public void fall() {
        y += speed;
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
        if (type != '0' && falling) {
            fall();
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void draw(Graphics g, ImageObserver obs) {
        if (visible) {
            g.drawImage(sprite[frame], x, y, obs);
        }
    }
}