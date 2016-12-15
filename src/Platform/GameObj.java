package Platform;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * Created by Bravolly Pich.
 */
public class GameObj {

    public int x, y, speed;
    public Rectangle box;
    public BufferedImage[] sprite;
    public boolean visible = true;
    public int frame = 0;
    public char collisionType = '~';


    /**
     * constructor that does nothing
     */
    public GameObj() {
    }

    /**
     * constructor that takes in the sprite and sets the dimensions
     * @param bImg BufferedImage array of object's sprite
     */
    public GameObj(BufferedImage[] bImg) {
        sprite = bImg;
        box = new Rectangle(sprite[0].getWidth(), sprite[0].getHeight());
    }

    /**
     * constructor that sets initial location
     * @param x location x
     * @param y location y
     */
    public GameObj(int x, int y) {
        this.x = x;
        this.y = y;
        box = new Rectangle(x, y, 0, 0);
    }

    /**
     * constructor that takes in the sprite and location then sets the dimensions
     * @param bImg BufferedImage array of object's sprite
     * @param x location x
     * @param y location y
     */
    public GameObj(BufferedImage[] bImg, int x, int y) {
        this.x = x;
        this.y = y;
        sprite = bImg;
        box = new Rectangle(x, y, sprite[0].getWidth(), sprite[0].getHeight());
    }

    /**
     * Sets the type of collision to be called in collisionAction()
     * @param type a number for the type of collision
     */
    public void setCollision(char type) {
        collisionType = type;
    }

    /**
     * Actions to be performed depending on the number from collisionType
     */
    public void collisionAction() {
        switch (collisionType) {
            default:
                break;
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
        box.x = x;
        box.y = y;
    }

    public void setX(int x) {
        this.x = x;
        box.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
        this.box.y = y;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds() {
        return box.getBounds();
    }

    public void setSprite(BufferedImage[] bImg) {
        sprite = bImg;
        box.width = sprite[0].getWidth();
        box.height = sprite[0].getHeight();
    }

    public void setVisible(boolean v) {
        visible = v;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setFrame(int i) {
        frame = i;
    }

    public void draw(Graphics g, ImageObserver obs) {
        if (visible) {
            g.drawImage(sprite[frame], x, y, obs);
        }
    }

}