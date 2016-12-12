package Platform;

import java.awt.event.KeyEvent;
import java.util.Observable;

/**
 * Created by Bravolly Pich.
 */
public class GameEvents extends Observable {

    public int type;
    public Object event;

    public void setValue(KeyEvent e) {
        type = 1; // let's assume this means key input.
        //Should use CONSTANT value for this when you program
        event = e;
        setChanged();
        // trigger notification
        notifyObservers(this);
    }

    public void setValue(String msg) {
        type = 2;
        event = msg;
        setChanged();
        // trigger notification
        notifyObservers(this);
    }
}