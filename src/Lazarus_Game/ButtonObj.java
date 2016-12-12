package Lazarus_Game;

import Platform.GameObj;

import java.awt.image.BufferedImage;
import java.util.Observer;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Bravolly Pich.
 */
public class ButtonObj extends GameObj implements Observer {

    public ButtonObj(BufferedImage[] sprite) {
        super(sprite);
    }

    public ButtonObj(BufferedImage[] sprite, int x, int y) {
        super(sprite);
        setX(x);
        setY(y);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
