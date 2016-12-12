package Lazarus_Game;

import Platform.GameObj;

import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Bravolly Pich on 8/31/2016.
 */
public class WoodBox extends GameObj implements Observer {
    boolean falling = false;

    public WoodBox(BufferedImage[] sprite) {
        super(sprite);
    }

    public WoodBox(BufferedImage[] sprite, int x, int y, int speed) {
        super(sprite);
        setX(x);
        setY(y);
        this.speed = speed;
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
                falling = true;
            default:
                break;
        }
    }

    public void fall() {
        setY(getY() + speed);
    }

    public boolean setFalling(boolean b) {
        return falling = b;
    }

    public boolean isFalling() {
        return falling;
    }

    public void update() {
        if (falling) {
            fall();
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
