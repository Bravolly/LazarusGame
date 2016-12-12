package Lazarus_Game;

import Platform.GameObj;

import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Bravolly Pich.
 */
public class WallObj extends GameObj implements Observer {

    public WallObj(BufferedImage[] sprite) {
        super(sprite);
    }

    public WallObj(BufferedImage[] sprite, int x, int y) {
        super(sprite);
        setX(x);
        setY(y);
    }

    /**
     * Actions to be performed depending on the number from collisionType
     */
    @Override
    public void collisionAction() {
        switch (collisionType) {
            default:
                break;
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}